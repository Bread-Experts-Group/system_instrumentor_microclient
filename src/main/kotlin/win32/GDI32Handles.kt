package org.bread_experts_group.win32

import org.bread_experts_group.getDowncall
import org.bread_experts_group.getLookup
import java.lang.foreign.Arena
import java.lang.foreign.Linker
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandle

private val localArena = Arena.ofAuto()
private val gdi32Lookup = localArena.getLookup("gdi32.dll")
private val linker: Linker = Linker.nativeLinker()

val nativeGDI32TextOutW: MethodHandle = gdi32Lookup.getDowncall(
	linker, "TextOutW", ValueLayout.JAVA_INT,
	ValueLayout.ADDRESS,
	ValueLayout.JAVA_INT,
	ValueLayout.JAVA_INT,
	ValueLayout.ADDRESS,
	ValueLayout.JAVA_INT
)

val nativeGDI32GetStockObject: MethodHandle = gdi32Lookup.getDowncall(
	linker, "GetStockObject", ValueLayout.ADDRESS,
	ValueLayout.JAVA_INT
)

val nativeGDI32SetBkMode: MethodHandle = gdi32Lookup.getDowncall(
	linker, "SetBkMode", ValueLayout.JAVA_INT,
	ValueLayout.ADDRESS, ValueLayout.JAVA_INT
)
val nativeGDI32SetTextColor: MethodHandle = gdi32Lookup.getDowncall(
	linker, "SetTextColor", ValueLayout.JAVA_INT,
	ValueLayout.ADDRESS, ValueLayout.JAVA_INT
)