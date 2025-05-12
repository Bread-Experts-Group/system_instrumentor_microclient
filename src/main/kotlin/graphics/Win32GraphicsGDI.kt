package org.bread_experts_group.graphics

import org.bread_experts_group.graphics.elements.DrawableElement
import org.bread_experts_group.graphics.elements.RasterImageElement
import org.bread_experts_group.graphics.elements.TextLabelElement
import org.bread_experts_group.win32.*
import org.bread_experts_group.windowing.Window
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

class Win32GraphicsGDI internal constructor(
	window: Window,
	private val windowHandle: MemorySegment
) : Graphics(window) {
	private val localArena = Arena.ofAuto()
	private val localElementsData: MutableMap<DrawableElement, Any> = mutableMapOf()

	override fun draw() {
		val paintStruct = this.localArena.allocate(win32PaintStruct)
		val hdc = nativeUser32BeginPaint.invokeExact(windowHandle, paintStruct) as MemorySegment
		if (hdc.address() == 0L) throw GraphicsOperationException()
		nativeGDI32SetBkMode.invokeExact(hdc, 0) as Int
		val clientRect = this.localArena.allocate(win32Rect)
		nativeUser32GetClientRect(windowHandle, clientRect) as Int
		val whiteBrush = nativeGDI32GetStockObject.invokeExact(4) as MemorySegment
		nativeUser32FillRect.invokeExact(hdc, clientRect, whiteBrush) as Int
		elements.forEach {
			when (it) {
				is TextLabelElement -> {
					nativeGDI32SetTextColor.invokeExact(hdc, it.foregroundColor.rawBGR) as Int
					nativeGDI32TextOutW.invokeExact(
						hdc, it.x, it.y,
						this.localArena.convertUtf16LE(it.text), it.text.length
					) as Int
				}

				is RasterImageElement -> {
					val bitmap = localElementsData.getOrPut(it) {
						val lDC = nativeGDI32CreateCompatibleDC(MemorySegment.NULL) as MemorySegment
						val bmI = this.localArena.allocate(win32GDIBitmapHeaderInfo)
						gdiBitmapInfoSize.set(bmI, 40)
						gdiBitmapInfoWidth.set(bmI, it.image.width)
						gdiBitmapInfoHeight.set(bmI, it.image.height)
						gdiBitmapInfoPlanes.set(bmI, 1.toShort())
						gdiBitmapInfoBitCount.set(bmI, 24.toShort())
						val bitsPointer = this.localArena.allocate(ValueLayout.ADDRESS)
						val nBM = nativeGDI32CreateDIBSection(
							lDC,
							bmI,
							0,
							bitsPointer,
							MemorySegment.NULL,
							0
						) as MemorySegment
						val stride = ((((it.image.width * 24) + 31) and 31.inv()) shr 3).toLong()
						val nativeBits = bitsPointer
							.get(ValueLayout.ADDRESS, 0)
							.reinterpret(stride * it.image.height)
						it.image.data.forEachIndexed { y, line ->
							val flipped = it.image.height - y - 1
							val nativeRow = nativeBits.asSlice(
								flipped * stride,
								stride
							)
							line.forEachIndexed { x, pixel ->
								val offset = x * 3L
								nativeRow.set(ValueLayout.JAVA_BYTE, offset, pixel[2])
								nativeRow.set(ValueLayout.JAVA_BYTE, offset + 1, pixel[1])
								nativeRow.set(ValueLayout.JAVA_BYTE, offset + 2, pixel[0])
							}
						}
						nBM
					} as MemorySegment
					val iDC = nativeGDI32CreateCompatibleDC(hdc) as MemorySegment
					val sBM = nativeGDI32SelectObject.invokeExact(iDC, bitmap) as MemorySegment
					val bmS = this.localArena.allocate(win32GDIBitmap)
					nativeGDI32GetObjectW.invokeExact(bitmap, win32GDIBitmap.byteSize().toInt(), bmS) as Int
					val status = nativeGDI32BitBlt.invokeExact(
						hdc, it.x, it.y, gdiBmWidth.get(bmS) as Int, gdiBmHeight.get(bmS) as Int,
						iDC, 0, 0,
						0x00CC0020
					) as Int
					if (status == 0) {
						checkLastError(this.localArena, this.logger)
						throw GraphicsOperationException()
					}
					nativeGDI32SelectObject.invokeExact(iDC, sBM) as MemorySegment
					nativeGDI32DeleteDC.invokeExact(iDC) as Int
				}

				else -> throw UnsupportedOperationException(it::class.simpleName)
			}
		}
		nativeUser32EndPaint.invokeExact(windowHandle, paintStruct) as Int
	}

	override fun close() {
	}
}