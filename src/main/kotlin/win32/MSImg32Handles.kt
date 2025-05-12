package org.bread_experts_group.win32

import org.bread_experts_group.getDowncall
import org.bread_experts_group.getLookup
import java.lang.foreign.Arena
import java.lang.foreign.Linker
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandle

private val localArena = Arena.ofAuto()
private val msImg32Lookup = localArena.getLookup("Msimg32.dll")
private val linker: Linker = Linker.nativeLinker()

val nativeMsImg32AlphaBlend: MethodHandle = msImg32Lookup.getDowncall(
	linker, "AlphaBlend", ValueLayout.JAVA_INT,
	ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT,
	ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT,
	ValueLayout.JAVA_INT
)