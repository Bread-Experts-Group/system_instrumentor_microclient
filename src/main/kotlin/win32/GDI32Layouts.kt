package org.bread_experts_group.win32

import java.lang.foreign.MemoryLayout
import java.lang.foreign.StructLayout
import java.lang.foreign.ValueLayout
import java.lang.invoke.VarHandle

val win32GDIBitmap: StructLayout = MemoryLayout.structLayout(
	ValueLayout.JAVA_INT.withName("bmType"),
	ValueLayout.JAVA_INT.withName("bmWidth"),
	ValueLayout.JAVA_INT.withName("bmHeight"),
	ValueLayout.JAVA_INT.withName("bmWidthBytes"),
	ValueLayout.JAVA_SHORT.withName("bmPlanes"),
	MemoryLayout.paddingLayout(2),
	ValueLayout.JAVA_SHORT.withName("bmBitsPixel"),
	MemoryLayout.paddingLayout(2),
	ValueLayout.ADDRESS.withName("bmBitsPixel")
)
val gdiBmWidth: VarHandle = win32GDIBitmap.varHandle(MemoryLayout.PathElement.groupElement("bmWidth"))
val gdiBmHeight: VarHandle = win32GDIBitmap.varHandle(MemoryLayout.PathElement.groupElement("bmHeight"))

val win32GDIBitmapHeaderInfo: StructLayout = MemoryLayout.structLayout(
	ValueLayout.JAVA_INT.withName("biSize"),
	ValueLayout.JAVA_INT.withName("biWidth"),
	ValueLayout.JAVA_INT.withName("biHeight"),
	ValueLayout.JAVA_SHORT.withName("biPlanes"),
	ValueLayout.JAVA_SHORT.withName("biBitCount"),
	ValueLayout.JAVA_INT.withName("biCompression"),
	ValueLayout.JAVA_INT.withName("biSizeImage"),
	ValueLayout.JAVA_INT.withName("biXPelsPerMeter"),
	ValueLayout.JAVA_INT.withName("biYPelsPerMeter"),
	ValueLayout.JAVA_INT.withName("biClrUsed"),
	ValueLayout.JAVA_INT.withName("biClrImportant")
)
val win32GDIBitmapInfo: StructLayout = MemoryLayout.structLayout(
	win32GDIBitmapHeaderInfo.withName("bmiHeader")
)
val gdiBitmapInfoSize: VarHandle = win32GDIBitmapInfo.varHandle(
	MemoryLayout.PathElement.groupElement("bmiHeader"),
	MemoryLayout.PathElement.groupElement("biSize")
)
val gdiBitmapInfoWidth: VarHandle = win32GDIBitmapInfo.varHandle(
	MemoryLayout.PathElement.groupElement("bmiHeader"),
	MemoryLayout.PathElement.groupElement("biWidth")
)
val gdiBitmapInfoHeight: VarHandle = win32GDIBitmapInfo.varHandle(
	MemoryLayout.PathElement.groupElement("bmiHeader"),
	MemoryLayout.PathElement.groupElement("biHeight")
)
val gdiBitmapInfoPlanes: VarHandle = win32GDIBitmapInfo.varHandle(
	MemoryLayout.PathElement.groupElement("bmiHeader"),
	MemoryLayout.PathElement.groupElement("biPlanes")
)
val gdiBitmapInfoBitCount: VarHandle = win32GDIBitmapInfo.varHandle(
	MemoryLayout.PathElement.groupElement("bmiHeader"),
	MemoryLayout.PathElement.groupElement("biBitCount")
)