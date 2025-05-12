package org.bread_experts_group.image

import org.bread_experts_group.image.png.header.ImageFormatHeader

abstract class RasterImage {
	class ImageParsingException(reason: String) : Exception(reason)

	abstract val width: Int
	abstract val height: Int
	abstract val bitDepth: Byte
	abstract val colorType: ImageFormatHeader.ColorType
	abstract val data: Array<Array<Array<Byte>>>
}