package org.bread_experts_group.graphics

import org.bread_experts_group.graphics.elements.TextLabel
import org.bread_experts_group.win32.*
import org.bread_experts_group.windowing.Window
import java.lang.foreign.Arena
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment

class Win32GraphicsGDI internal constructor(
	window: Window,
	private val windowHandle: MemorySegment
) : Graphics(window) {
	private val localArena = Arena.ofAuto()
	private val linker: Linker = Linker.nativeLinker()
	private val deviceContext: MemorySegment = nativeUser32GetDC.invokeExact(windowHandle) as MemorySegment

	override fun draw() {
		val paintStruct = this.localArena.allocate(win32PaintStruct)
		val hdc = nativeUser32BeginPaint.invokeExact(windowHandle, paintStruct) as MemorySegment
		if (hdc == MemorySegment.NULL) throw GraphicsOperationException()
		nativeGDI32SetBkMode.invokeExact(hdc, 0) as Int
		val clientRect = this.localArena.allocate(win32Rect)
		nativeUser32GetClientRect(windowHandle, clientRect) as Int
		val whiteBrush = nativeGDI32GetStockObject.invokeExact(4) as MemorySegment
		nativeUser32FillRect.invokeExact(hdc, clientRect, whiteBrush) as Int
		elements.forEach {
			when (it) {
				is TextLabel -> {
					nativeGDI32SetTextColor.invokeExact(hdc, it.foregroundColor.rawBGR) as Int
					nativeGDI32TextOutW.invokeExact(
						hdc, it.x, it.y,
						this.localArena.convertUtf16LE(it.text), it.text.length
					) as Int
				}

				else -> throw UnsupportedOperationException(it::class.simpleName)
			}
		}
		nativeGDI32TextOutW.invokeExact(hdc, 0, 0, this.localArena.convertUtf16LE("Test ばやちゃお Test"), 15) as Int
		nativeUser32EndPaint.invokeExact(windowHandle, paintStruct) as Int
	}

	override fun close() {
	}
}