package org.bread_experts_group.image.png.header

import java.io.DataInputStream

sealed class PortableNetworkGraphicsHeader(
	open val name: String
) {
	companion object {
		fun read(name: String, stream: DataInputStream): PortableNetworkGraphicsHeader? = when (name) {
			"IHDR" -> ImageFormatHeader(stream)
			"IEND" -> null
			"tEXt" -> TextualHeader(stream)
			"zTXt" -> CompressedTextualHeader(stream)
			"pHYs" -> PhysicalPixelDimensionsHeader(stream)
			else ->
				if (name[0].isUpperCase()) throw UnsupportedOperationException(name)
				else GenericHeader(name, stream)
		}
	}
}