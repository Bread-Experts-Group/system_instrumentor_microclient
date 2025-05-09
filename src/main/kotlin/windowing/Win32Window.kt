package org.bread_experts_group.windowing

import org.bread_experts_group.debugString
import org.bread_experts_group.mainLogger
import java.lang.foreign.Arena
import java.lang.foreign.FunctionDescriptor
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import java.util.logging.Logger

class Win32Window : Window() {
	private val localArena = Arena.ofAuto()
	private val windowLogger = Logger.getLogger("Win32 Window")

	fun windowProcess(handle: MemorySegment, message: Int, wParam: Long, lParam: Long): Long {
		mainLogger.info("Got window event ${handle.debugString()} / $message / $wParam / $lParam")
		return nativeUser32DefWindowProcA.invokeExact(handle, message, wParam, lParam) as Long
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
		val atom = nativeUser32RegisterClassExA.invokeExact(classStructure) as Short
		windowLogger.info("Class registered under $atom")
		val windowHandle = nativeUser32CreateWindowExA.invokeExact(
			0,
			className,
			this.localArena.allocateUtf8String("ばやちゃお。。。。 save me"),
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
		windowLogger.info("Window created at handle ${windowHandle.debugString()}")
		nativeUser32ShowWindow.invokeExact(
			windowHandle,
			1
		) as Int
	}

	override fun close() {
		TODO("Not yet implemented")
	}
}