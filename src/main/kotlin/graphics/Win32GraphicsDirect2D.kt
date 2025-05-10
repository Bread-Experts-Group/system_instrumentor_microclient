package org.bread_experts_group.graphics

import org.bread_experts_group.win32.*
import org.bread_experts_group.windowing.Window
import java.lang.foreign.*
import java.lang.invoke.MethodHandle

class Win32GraphicsDirect2D internal constructor(
	window: Window,
	private val windowHandle: MemorySegment
) : Graphics(window) {
	private val localArena = Arena.ofAuto()
	private val d2d1Factory: MemorySegment = this.localArena.allocate(ValueLayout.ADDRESS)
	private val d2d1HwndRenderTarget: MemorySegment = this.localArena.allocate(ValueLayout.ADDRESS)
	private val d2d1RTBrush: MemorySegment = this.localArena.allocate(ValueLayout.ADDRESS)
	private val linker: Linker = Linker.nativeLinker()

	private fun createFactory() {
		val status = nativeDirect2DCreateFactory.invokeExact(
			1,
			direct2DCOMUUID,
			MemorySegment.NULL,
			d2d1Factory
		) as Int
		if (checkLastError(this.localArena, this.logger, status))
			throw GraphicsOperationException()
	}

	private fun createRenderTarget() {
		val clientRect = this.localArena.allocate(win32Rect)
		nativeUser32GetClientRect.invokeExact(
			windowHandle,
			clientRect
		) as Int
		val rtp = this.localArena.allocate(win32D2D1RenderTargetProperties)
		d2D1RTPPixelFormat.set(rtp, 87)
		d2D1RTPPixelAlphaMode.set(rtp, 1)
		val hrtp = this.localArena.allocate(win32D2D1HwndRenderTargetProperties)
		d2D1HwndRTPHwnd.set(hrtp, windowHandle)
		val pixelSizeOffset = win32D2D1HwndRenderTargetProperties
			.byteOffset(MemoryLayout.PathElement.groupElement("pixelSize"))
		val pixelSizeSegment = hrtp.asSlice(pixelSizeOffset, win32D2DSizeU.byteSize())
		d2DSizeUWidthHandle.set(
			pixelSizeSegment,
			(rectRightHandle.get(clientRect) as Int) - (rectLeftHandle.get(clientRect) as Int)
		)
		d2DSizeUHeightHandle.set(
			pixelSizeSegment,
			(rectBottomHandle.get(clientRect) as Int) - (rectTopHandle.get(clientRect) as Int)
		)
		val actual = d2d1Factory.get(ValueLayout.ADDRESS, 0).reinterpret(8L)
		val method = actual.getMethod(
			linker, 10,
			ValueLayout.JAVA_INT,
			ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS
		)
		val inPointer = this.localArena.allocate(ValueLayout.ADDRESS)
		val status = method.invokeExact(actual, rtp, hrtp, inPointer) as Int
		d2d1HwndRenderTarget.set(ValueLayout.ADDRESS, 0, inPointer)
		if (checkLastError(this.localArena, this.logger, status))
			throw GraphicsOperationException()
	}

	private fun createBrush() {
		val color = this.localArena.allocate(win32D3DColorValue)
		d3DColorB.set(color, 1f)
		d3DColorA.set(color, 0.5f)
		val actual = d2d1Factory.get(ValueLayout.ADDRESS, 0).reinterpret(8L)
		val method = actual.getMethod(
			linker, 35,
			ValueLayout.JAVA_INT,
			ValueLayout.ADDRESS, ValueLayout.ADDRESS
		)
		method.invokeExact(actual, color, d2d1RTBrush) as Int
	}

	private val rtDraw: MethodHandle
//	private val rtEndDraw: MethodHandle

	init {
		createFactory()
		createRenderTarget()
		createBrush()
		rtDraw = d2d1HwndRenderTarget.getMethodVoid(linker, 2)
//		rtEndDraw = inPointer.getMethodVoid(linker, 1)
	}

	override fun draw() {
		rtDraw.invokeExact(d2d1HwndRenderTarget)
	}

	override fun close() {
	}
}