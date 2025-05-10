package org.bread_experts_group.win32

import org.bread_experts_group.getDowncall
import org.bread_experts_group.getDowncallVoid
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout
import java.lang.invoke.MethodHandle

fun MemorySegment.getMethod(
	linker: Linker, selection: Long,
	returnLayout: ValueLayout,
	vararg layouts: ValueLayout
): MethodHandle {
	val vtable = this.get(ValueLayout.ADDRESS, 0).reinterpret((selection + 1) * 8)
	val fnPtr = vtable.getAtIndex(ValueLayout.ADDRESS, selection)
	return fnPtr.getDowncall(linker, returnLayout, ValueLayout.ADDRESS, *layouts)
}

fun MemorySegment.getMethodVoid(
	linker: Linker, selection: Long,
	vararg layouts: ValueLayout
): MethodHandle {
	val vtable = this.get(ValueLayout.ADDRESS, 0).reinterpret((selection + 1) * 8)
	val fnPtr = vtable.getAtIndex(ValueLayout.ADDRESS, selection)
	return fnPtr.getDowncallVoid(linker, ValueLayout.ADDRESS, *layouts)
}