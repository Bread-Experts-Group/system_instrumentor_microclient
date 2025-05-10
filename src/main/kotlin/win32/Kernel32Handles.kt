package org.bread_experts_group.win32

import org.bread_experts_group.getDowncall
import org.bread_experts_group.getDowncallVoid
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

val nativeKernel32FormatMessageW: MethodHandle = kernel32Lookup.getDowncall(
	linker, "FormatMessageW", ValueLayout.JAVA_INT,
	ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS,
	ValueLayout.JAVA_INT, ValueLayout.ADDRESS
)

val nativeKernel32GetUserDefaultLangID: MethodHandle = kernel32Lookup.getDowncall(
	linker, "GetUserDefaultLangID", ValueLayout.JAVA_INT
)

val nativeKernel32GetLastError: MethodHandle = kernel32Lookup.getDowncall(
	linker, "GetLastError", ValueLayout.JAVA_INT
)
val nativeKernel32SetLastError: MethodHandle = kernel32Lookup.getDowncallVoid(
	linker, "SetLastError", ValueLayout.JAVA_INT
)