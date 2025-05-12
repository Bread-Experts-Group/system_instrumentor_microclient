package org.bread_experts_group.windowing

import org.bread_experts_group.debugString
import org.bread_experts_group.graphics.Graphics
import org.bread_experts_group.graphics.Win32GraphicsGDI
import org.bread_experts_group.win32.*
import java.lang.foreign.*
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import java.util.concurrent.CountDownLatch

class Win32Window(title: String) : Window() {
	private val localArena = Arena.ofAuto()
	private lateinit var windowHandle: MemorySegment
	override val graphics: Graphics

	override var title: String
		get() {
			ensureOpen()
			val length = nativeUser32GetWindowTextLengthW.invokeExact(
				this.windowHandle
			) as Int
			if (length == 0 && checkLastError(this.localArena, this.logger))
				throw WindowOperationException()
			val buffer = this.localArena.allocate((length.toLong() + 1) * 2)
			nativeUser32GetWindowTextW.invokeExact(
				this.windowHandle,
				buffer,
				length + 1
			) as Int
			if (checkLastError(this.localArena, this.logger))
				throw WindowOperationException()
			return buffer
				.toArray(ValueLayout.JAVA_BYTE)
				.toString(Charsets.UTF_16LE)
				.take(length)
		}
		set(value) {
			ensureOpen()
			val result = nativeUser32SetWindowTextW.invokeExact(
				this.windowHandle,
				this.localArena.convertUtf16LE(value)
			) as Int
			if (result == 0 && checkLastError(this.localArena, this.logger))
				throw WindowOperationException()
		}

	@Suppress("UNUSED")
	fun windowProcess(handle: MemorySegment, message: Int, wParam: Long, lParam: Long): Long = when (message) {
		0xF -> {
			this.graphics.draw()
			0
		}

		else -> {
			logger.finest { "Got window event ${handle.debugString()} / $message / $wParam / $lParam" }
			nativeUser32DefWindowProcW.invokeExact(handle, message, wParam, lParam) as Long
		}
	}

	init {
		val classStructure = this.localArena.allocate(win32WndClassA)
		wndClassCbSizeHandle.set(classStructure, classStructure.byteSize().toInt())
		wndClassHInstanceHandle.set(classStructure, currentProcessHandle)
		val className = this.localArena.allocateUtf8String("BreadServerLibraryWin32 Window")
		wndClassLpszClassNameHandle.set(classStructure, className)
		val linker = Linker.nativeLinker()
		val methodLookup = MethodHandles.lookup()
		lpfnWndProcHandle.set(
			classStructure,
			linker.upcallStub(
				methodLookup.bind(
					this, "windowProcess",
					MethodType.methodType(
						Long::class.javaPrimitiveType,
						MemorySegment::class.java, Int::class.javaPrimitiveType,
						Long::class.javaPrimitiveType, Long::class.javaPrimitiveType
					)
				),
				FunctionDescriptor.of(
					ValueLayout.JAVA_LONG,
					ValueLayout.ADDRESS, ValueLayout.JAVA_INT,
					ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG
				),
				this.localArena
			)
		)
		val atom = nativeUser32RegisterClassExW.invokeExact(classStructure) as Short
		if (atom.toInt() == 0 && checkLastError(this.localArena, this.logger))
			throw WindowOperationException()
		logger.fine { "Class registered under $atom" }
		val sync = CountDownLatch(1)
		Thread.ofPlatform().name("Window-Dispatcher").start {
			windowHandle = nativeUser32CreateWindowExW.invokeExact(
				0,
				className,
				this.localArena.convertUtf16LE(title),
				0x00CF0000,
				Int.MIN_VALUE,
				Int.MIN_VALUE,
				Int.MIN_VALUE,
				Int.MIN_VALUE,
				MemorySegment.NULL,
				MemorySegment.NULL,
				currentProcessHandle,
				MemorySegment.NULL
			) as MemorySegment
			if (windowHandle.address() == 0L) {
				checkLastError(this.localArena, this.logger)
				throw WindowOperationException()
			}
			logger.fine { "Window created at handle ${windowHandle.debugString()}" }
			sync.countDown()
			val message = this.localArena.allocate(win32Msg)
			while (nativeUser32GetMessageW.invokeExact(message, windowHandle, 0, 0) as Int > 0) {
				nativeUser32TranslateMessage.invokeExact(message) as Int
				nativeUser32DispatchMessageW.invokeExact(message) as Long
			}
		}
		sync.await()
		graphics = Win32GraphicsGDI(this, this.windowHandle)
	}

	private fun nativeShow(n: Int) {
		ensureOpen()
		val status = nativeUser32ShowWindow.invokeExact(
			windowHandle,
			n
		) as Int
		if (status != 0) {
			logger.severe { status.retrieveMessage(this.localArena) }
			throw WindowOperationException()
		}
	}

	override fun show() = nativeShow(1)
	override fun hide() = nativeShow(0)
	override fun minimize() = nativeShow(2)
	override fun maximize() = nativeShow(3)
	override fun restore() = nativeShow(9)

	override fun close() {
		closed = true
		this.graphics.close()
		val returned = nativeUser32PostMessageW.invokeExact(
			this.windowHandle,
			0x10,
			0L,
			0L
		) as Int
		if (returned == 0 && checkLastError(this.localArena, this.logger))
			throw WindowOperationException()
	}
}