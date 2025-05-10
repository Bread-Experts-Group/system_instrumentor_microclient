package org.bread_experts_group.win32

import org.bread_experts_group.hex
import java.lang.foreign.AddressLayout
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout
import java.util.logging.Logger

fun Arena.convertUtf16LE(string: String): MemorySegment = this.allocateArray(
	ValueLayout.JAVA_BYTE,
	*string.toByteArray(Charsets.UTF_16LE),
	0,
	0
)

fun Int.retrieveMessage(arena: Arena): String {
	val ptr = arena.allocate(AddressLayout.ADDRESS)
	val stored = nativeKernel32FormatMessageW.invokeExact(
		0x00003300,
		MemorySegment.NULL,
		this,
		nativeKernel32GetUserDefaultLangID.invokeExact() as Int,
		ptr,
		0,
		MemorySegment.NULL
	) as Int
	val base = "[${hex(this.toUInt())}]"
	if (stored == 0) return base
	return "$base " + ptr
		.get(AddressLayout.ADDRESS, 0)
		.reinterpret((stored - 1) * 2L)
		.toArray(ValueLayout.JAVA_BYTE)
		.toString(Charsets.UTF_16LE)
}

fun checkLastError(arena: Arena, logger: Logger, errNo: Int? = null): Boolean {
	val error = if (errNo != null) errNo
	else {
		val saved = nativeKernel32GetLastError.invokeExact() as Int
		nativeKernel32SetLastError.invokeExact(0)
		saved
	}
	if (error != 0) logger.severe(error.retrieveMessage(arena))
	else return false
	return true
}