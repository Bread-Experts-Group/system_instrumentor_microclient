package org.bread_experts_group.graphics.elements

import org.bread_experts_group.image.RasterImage

data class RasterImageElement(
	val image: RasterImage,
	override var x: Int = 0,
	override var y: Int = 0
) : DrawableElement