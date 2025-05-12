package org.bread_experts_group.graphics.elements

data class TextLabelElement(
	var text: String,
	override var foregroundColor: Color = Color(0u, 0u, 0u),
	override var x: Int = 0,
	override var y: Int = 0
) : DrawableElement, ForegroundColoredElement