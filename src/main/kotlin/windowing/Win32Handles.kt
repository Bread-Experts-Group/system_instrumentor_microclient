package org.bread_experts_group.windowing

import org.bread_experts_group.getDowncall
import org.bread_experts_group.getLookup
import java.lang.foreign.Arena
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandle

private val localArena = Arena.ofAuto()
private val kernel32Lookup = localArena.getLookup("kernel32.dll")
private val linker: Linker = Linker.nativeLinker()

val nativeKernel32GetModuleHandleW: MethodHandle = kernel32Lookup.getDowncall(
	linker, "GetModuleHandleW", ValueLayout.ADDRESS,
	ValueLayout.ADDRESS
)
val currentProcessHandle = nativeKernel32GetModuleHandleW.invokeExact(MemorySegment.NULL) as MemorySegment

private val user32Lookup = localArena.getLookup("User32.dll")
val nativeUser32RegisterClassExA: MethodHandle = user32Lookup.getDowncall(
	linker, "RegisterClassExA", ValueLayout.JAVA_SHORT,
	ValueLayout.ADDRESS
)
val nativeUser32CreateWindowExA: MethodHandle = user32Lookup.getDowncall(
	linker, "CreateWindowExA", ValueLayout.ADDRESS,
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
val nativeUser32DefWindowProcA: MethodHandle = user32Lookup.getDowncall(
	linker, "DefWindowProcA", ValueLayout.JAVA_LONG,
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