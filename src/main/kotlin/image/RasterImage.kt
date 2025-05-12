package org.bread_experts_group.image

import java.util.logging.Logger

abstract class RasterImage {
	protected val logger: Logger = Logger.getLogger("Raster Image")

	class ImageParsingException(reason: String) : Exception(reason)

	abstract val width: Int
	abstract val height: Int
	abstract val bitDepth: Byte
	abstract val data: Array<Array<Array<Byte>>>
}