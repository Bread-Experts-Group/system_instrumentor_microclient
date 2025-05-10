package org.bread_experts_group.graphics

import org.bread_experts_group.windowing.Window

class NoOpGraphics internal constructor(window: Window) : Graphics(window) {
	override fun draw() {
	}

	override fun close() {
	}
}