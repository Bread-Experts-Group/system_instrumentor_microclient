package org.bread_experts_group.win32

import org.bread_experts_group.getDowncall
import org.bread_experts_group.getLookup
import java.lang.foreign.Arena
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandle

private val localArena = Arena.ofAuto()
private val direct2DLookup = localArena.getLookup("D2d1.dll")
private val linker: Linker = Linker.nativeLinker()

val direct2DCOMUUID: MemorySegment = localArena.allocate(16).also {
	it.set(ValueLayout.JAVA_INT, 0, 0x06152247)
	it.set(ValueLayout.JAVA_SHORT, 4, 0x6F50)
	it.set(ValueLayout.JAVA_SHORT, 6, 0x465A)
	it.set(ValueLayout.JAVA_BYTE, 8, 0x92.toByte())
	it.set(ValueLayout.JAVA_BYTE, 9, 0x45.toByte())
	it.set(ValueLayout.JAVA_BYTE, 10, 0x11.toByte())
	it.set(ValueLayout.JAVA_BYTE, 11, 0x8B.toByte())
	it.set(ValueLayout.JAVA_BYTE, 12, 0xFD.toByte())
	it.set(ValueLayout.JAVA_BYTE, 13, 0x3B.toByte())
	it.set(ValueLayout.JAVA_BYTE, 14, 0x60.toByte())
	it.set(ValueLayout.JAVA_BYTE, 15, 0x07.toByte())
}

val nativeDirect2DCreateFactory: MethodHandle = direct2DLookup.getDowncall(
	linker, "D2D1CreateFactory", ValueLayout.JAVA_INT,
	ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS
)