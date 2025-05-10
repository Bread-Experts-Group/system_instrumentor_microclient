package org.bread_experts_group.win32

import java.lang.foreign.MemoryLayout
import java.lang.foreign.StructLayout
import java.lang.foreign.ValueLayout
import java.lang.invoke.VarHandle


val win32WndClassA: StructLayout = MemoryLayout.structLayout(
	ValueLayout.JAVA_INT.withName("cbSize"),
	ValueLayout.JAVA_INT.withName("style"),
	ValueLayout.ADDRESS.withName("lpfnWndProc"),
	ValueLayout.JAVA_INT.withName("cbClsExtra"),
	ValueLayout.JAVA_INT.withName("cbWndExtra"),
	ValueLayout.ADDRESS.withName("hInstance"),
	ValueLayout.ADDRESS.withName("hIcon"),
	ValueLayout.ADDRESS.withName("hCursor"),
	ValueLayout.ADDRESS.withName("hbrBackground"),
	ValueLayout.ADDRESS.withName("lpszMenuName"),
	ValueLayout.ADDRESS.withName("lpszClassName"),
	ValueLayout.ADDRESS.withName("hIconSm")
)
val wndClassCbSizeHandle: VarHandle = win32WndClassA.varHandle(MemoryLayout.PathElement.groupElement("cbSize"))
val wndClassStyleHandle: VarHandle = win32WndClassA.varHandle(MemoryLayout.PathElement.groupElement("style"))
val lpfnWndProcHandle: VarHandle = win32WndClassA.varHandle(MemoryLayout.PathElement.groupElement("lpfnWndProc"))
val wndClassCbClsExtraHandle: VarHandle = win32WndClassA.varHandle(MemoryLayout.PathElement.groupElement("cbClsExtra"))
val wndClassCbWndExtraHandle: VarHandle = win32WndClassA.varHandle(MemoryLayout.PathElement.groupElement("cbWndExtra"))
val wndClassHInstanceHandle: VarHandle = win32WndClassA.varHandle(MemoryLayout.PathElement.groupElement("hInstance"))
val wndClassHIconHandle: VarHandle = win32WndClassA.varHandle(MemoryLayout.PathElement.groupElement("hIcon"))
val wndClassHCursorHandle: VarHandle = win32WndClassA.varHandle(MemoryLayout.PathElement.groupElement("hCursor"))
val wndClassHBrBackgroundHandle: VarHandle = win32WndClassA.varHandle(MemoryLayout.PathElement.groupElement("hbrBackground"))
val wndClassLpszMenuNameHandle: VarHandle = win32WndClassA.varHandle(MemoryLayout.PathElement.groupElement("lpszMenuName"))
val wndClassLpszClassNameHandle: VarHandle = win32WndClassA.varHandle(MemoryLayout.PathElement.groupElement("lpszClassName"))
val wndClassHIconSmHandle: VarHandle = win32WndClassA.varHandle(MemoryLayout.PathElement.groupElement("hIconSm"))

val win32Point: StructLayout = MemoryLayout.structLayout(
	ValueLayout.JAVA_INT.withName("x"),
	ValueLayout.JAVA_INT.withName("y")
)

val win32Rect: StructLayout = MemoryLayout.structLayout(
	ValueLayout.JAVA_INT.withName("left"),
	ValueLayout.JAVA_INT.withName("top"),
	ValueLayout.JAVA_INT.withName("right"),
	ValueLayout.JAVA_INT.withName("bottom")
)
val rectLeftHandle: VarHandle = win32Rect.varHandle(MemoryLayout.PathElement.groupElement("left"))
val rectTopHandle: VarHandle = win32Rect.varHandle(MemoryLayout.PathElement.groupElement("top"))
val rectRightHandle: VarHandle = win32Rect.varHandle(MemoryLayout.PathElement.groupElement("right"))
val rectBottomHandle: VarHandle = win32Rect.varHandle(MemoryLayout.PathElement.groupElement("bottom"))

val win32Msg: StructLayout = MemoryLayout.structLayout(
	ValueLayout.ADDRESS.withName("hwnd"),
	MemoryLayout.paddingLayout(4),
	ValueLayout.JAVA_INT.withName("message"),
	ValueLayout.JAVA_LONG.withName("wParam"),
	ValueLayout.JAVA_LONG.withName("lParam"),
	win32Point.withName("time"),
	ValueLayout.JAVA_INT.withName("lPrivate")
)

val win32D2DSizeU: StructLayout = MemoryLayout.structLayout(
	ValueLayout.JAVA_INT.withName("width"),
	ValueLayout.JAVA_INT.withName("height")
)
val d2DSizeUWidthHandle: VarHandle = win32D2DSizeU.varHandle(MemoryLayout.PathElement.groupElement("width"))
val d2DSizeUHeightHandle: VarHandle = win32D2DSizeU.varHandle(MemoryLayout.PathElement.groupElement("height"))

val win32D2D1PixelFormat: StructLayout = MemoryLayout.structLayout(
	ValueLayout.JAVA_INT.withName("format"),
	ValueLayout.JAVA_INT.withName("alphaMode")
)

val win32D2D1RenderTargetProperties: StructLayout = MemoryLayout.structLayout(
	ValueLayout.JAVA_INT.withName("type"),
	win32D2D1PixelFormat.withName("pixelFormat"),
	ValueLayout.JAVA_FLOAT.withName("dpiX"),
	ValueLayout.JAVA_FLOAT.withName("dpiY"),
	ValueLayout.JAVA_INT.withName("usage"),
	ValueLayout.JAVA_INT.withName("minLevel"),
)
val d2D1RTPType: VarHandle = win32D2D1RenderTargetProperties.varHandle(MemoryLayout.PathElement.groupElement("type"))
val d2D1RTPPixelFormat: VarHandle = win32D2D1RenderTargetProperties.varHandle(
	MemoryLayout.PathElement.groupElement("pixelFormat"),
	MemoryLayout.PathElement.groupElement("format")
)
val d2D1RTPPixelAlphaMode: VarHandle = win32D2D1RenderTargetProperties.varHandle(
	MemoryLayout.PathElement.groupElement("pixelFormat"),
	MemoryLayout.PathElement.groupElement("alphaMode")
)
val d2D1RTPDpiX: VarHandle = win32D2D1RenderTargetProperties.varHandle(MemoryLayout.PathElement.groupElement("dpiX"))
val d2D1RTPDpiY: VarHandle = win32D2D1RenderTargetProperties.varHandle(MemoryLayout.PathElement.groupElement("dpiY"))
val d2D1RTPUsage: VarHandle = win32D2D1RenderTargetProperties.varHandle(MemoryLayout.PathElement.groupElement("usage"))
val d2D1RTPMinLevel: VarHandle =
	win32D2D1RenderTargetProperties.varHandle(MemoryLayout.PathElement.groupElement("minLevel"))

val win32D2D1HwndRenderTargetProperties: StructLayout = MemoryLayout.structLayout(
	ValueLayout.ADDRESS.withName("hwnd"),
	win32D2DSizeU.withName("pixelSize"),
	ValueLayout.JAVA_INT.withName("presentOptions")
)
val d2D1HwndRTPHwnd: VarHandle =
	win32D2D1HwndRenderTargetProperties.varHandle(MemoryLayout.PathElement.groupElement("hwnd"))

val win32D3DColorValue: StructLayout = MemoryLayout.structLayout(
	ValueLayout.JAVA_FLOAT.withName("r"),
	ValueLayout.JAVA_FLOAT.withName("g"),
	ValueLayout.JAVA_FLOAT.withName("b"),
	ValueLayout.JAVA_FLOAT.withName("a")
)
val d3DColorR: VarHandle = win32D3DColorValue.varHandle(MemoryLayout.PathElement.groupElement("r"))
val d3DColorG: VarHandle = win32D3DColorValue.varHandle(MemoryLayout.PathElement.groupElement("g"))
val d3DColorB: VarHandle = win32D3DColorValue.varHandle(MemoryLayout.PathElement.groupElement("b"))
val d3DColorA: VarHandle = win32D3DColorValue.varHandle(MemoryLayout.PathElement.groupElement("a"))