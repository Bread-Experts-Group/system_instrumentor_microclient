package org.bread_experts_group.graphics

import org.bread_experts_group.graphics.elements.DrawableElement
import org.bread_experts_group.windowing.Window
import java.util.logging.Logger

sealed class Graphics(window: Window) : AutoCloseable {
	protected val logger: Logger = Logger.getLogger("Graphics")
	val elements: MutableList<DrawableElement> = mutableListOf()

	abstract fun draw()

	class GraphicsOperationException : Exception()
}