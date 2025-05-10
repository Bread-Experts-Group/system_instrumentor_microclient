package org.bread_experts_group.win32

import org.bread_experts_group.getDowncall
import org.bread_experts_group.getLookup
import java.lang.foreign.Arena
import java.lang.foreign.Linker
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandle

private val localArena = Arena.ofAuto()
private val user32Lookup = localArena.getLookup("User32.dll")
private val linker: Linker = Linker.nativeLinker()

val nativeUser32RegisterClassExW: MethodHandle = user32Lookup.getDowncall(
	linker, "RegisterClassExW", ValueLayout.JAVA_SHORT,
	ValueLayout.ADDRESS
)
val nativeUser32CreateWindowExW: MethodHandle = user32Lookup.getDowncall(
	linker, "CreateWindowExW", ValueLayout.ADDRESS,
	ValueLayout.JAVA_INT,
	ValueLayout.ADDRESS,
	ValueLayout.ADDRESS,
	ValueLayout.JAVA_INT,
	ValueLayout.JAVA_INT,
	ValueLayout.JAVA_INT,
	ValueLayout.JAVA_INT,
	ValueLayout.JAVA_INT,
	ValueLayout.ADDRESS,
	ValueLayout.ADDRESS,
	ValueLayout.ADDRESS,
	ValueLayout.ADDRESS
)
val nativeUser32DefWindowProcW: MethodHandle = user32Lookup.getDowncall(
	linker, "DefWindowProcW", ValueLayout.JAVA_LONG,
	ValueLayout.ADDRESS,
	ValueLayout.JAVA_INT,
	ValueLayout.JAVA_LONG,
	ValueLayout.JAVA_LONG
)
val nativeUser32ShowWindow: MethodHandle = user32Lookup.getDowncall(
	linker, "ShowWindow", ValueLayout.JAVA_INT,
	ValueLayout.ADDRESS,
	ValueLayout.JAVA_INT
)
val nativeUser32PostMessageW: MethodHandle = user32Lookup.getDowncall(
	linker, "PostMessageW", ValueLayout.JAVA_INT,
	ValueLayout.ADDRESS,
	ValueLayout.JAVA_INT,
	ValueLayout.JAVA_LONG,
	ValueLayout.JAVA_LONG
)
val nativeUser32GetWindowTextW: MethodHandle = user32Lookup.getDowncall(
	linker, "GetWindowTextW", ValueLayout.JAVA_INT,
	ValueLayout.ADDRESS,
	ValueLayout.ADDRESS,
	ValueLayout.JAVA_INT
)
val nativeUser32GetWindowTextLengthW: MethodHandle = user32Lookup.getDowncall(
	linker, "GetWindowTextLengthW", ValueLayout.JAVA_INT,
	ValueLayout.ADDRESS
)
val nativeUser32SetWindowTextW: MethodHandle = user32Lookup.getDowncall(
	linker, "SetWindowTextW", ValueLayout.JAVA_INT,
	ValueLayout.ADDRESS,
	ValueLayout.ADDRESS
)
val nativeUser32GetMessageW: MethodHandle = user32Lookup.getDowncall(
	linker, "GetMessageW", ValueLayout.JAVA_INT,
	ValueLayout.ADDRESS,
	ValueLayout.ADDRESS,
	ValueLayout.JAVA_INT,
	ValueLayout.JAVA_INT
)
val nativeUser32TranslateMessage: MethodHandle = user32Lookup.getDowncall(
	linker, "TranslateMessage", ValueLayout.JAVA_INT,
	ValueLayout.ADDRESS
)
val nativeUser32DispatchMessageW: MethodHandle = user32Lookup.getDowncall(
	linker, "DispatchMessageW", ValueLayout.JAVA_LONG,
	ValueLayout.ADDRESS
)
val nativeUser32GetClientRect: MethodHandle = user32Lookup.getDowncall(
	linker, "GetClientRect", ValueLayout.JAVA_INT,
	ValueLayout.ADDRESS,
	ValueLayout.ADDRESS
)
val nativeUser32BeginPaint: MethodHandle = user32Lookup.getDowncall(
	linker, "BeginPaint", ValueLayout.ADDRESS,
	ValueLayout.ADDRESS,
	ValueLayout.ADDRESS
)
val nativeUser32EndPaint: MethodHandle = user32Lookup.getDowncall(
	linker, "EndPaint", ValueLayout.JAVA_INT,
	ValueLayout.ADDRESS,
	ValueLayout.ADDRESS
)
val nativeUser32FillRect: MethodHandle = user32Lookup.getDowncall(
	linker, "FillRect", ValueLayout.JAVA_INT,
	ValueLayout.ADDRESS,
	ValueLayout.ADDRESS,
	ValueLayout.ADDRESS
)
val nativeUser32GetDC: MethodHandle = user32Lookup.getDowncall(
	linker, "GetDC", ValueLayout.ADDRESS,
	ValueLayout.ADDRESS
)