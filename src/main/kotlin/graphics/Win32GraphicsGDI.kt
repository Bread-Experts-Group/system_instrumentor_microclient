package org.bread_experts_group.graphics

import org.bread_experts_group.windowing.Window
import java.lang.foreign.Arena
import java.lang.foreign.Linker
import java.lang.foreign.MemorySegment

class Win32GraphicsGDI internal constructor(
	window: Window,
	private val windowHandle: MemorySegment
) : Graphics(window) {
	private val localArena = Arena.ofAuto()
	private val linker: Linker = Linker.nativeLinker()

	init {
	}

	override fun draw() {
	}

	override fun close() {
	}
}