package org.bread_experts_group.windowing

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