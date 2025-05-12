package org.bread_experts_group.image.png.header

import org.bread_experts_group.image.RasterImage
import java.io.DataInputStream

data class ImageFormatHeader(
	val width: Int,
	val height: Int,
	val bitDepth: Byte,
	val colorType: ColorType,
	val compressionMethod: CompressionMethod,
	val filterMethod: FilterMethod,
	val interlaceMethod: InterlaceMethod
) : PortableNetworkGraphicsHeader("IHDR") {
	enum class ColorType(val code: Byte, val allowedDepths: Array<Byte>) {
		GRAYSCALE(0, arrayOf(1, 2, 4, 8, 16)),
		RGB_TRIPLET(2, arrayOf(8, 16)),
		PALETTE_INDEXED(3, arrayOf(1, 2, 4, 8)),
		GRAYSCALE_WITH_ALPHA(4, arrayOf(8, 16)),
		RGB_TRIPLET_WITH_ALPHA(6, arrayOf(8, 16));

		companion object {
			val mapping = entries.associateBy(ColorType::code)
		}
	}

	enum class CompressionMethod(val code: Byte) {
		DEFLATE(0);

		companion object {
			val mapping = entries.associateBy(CompressionMethod::code)
		}
	}

	enum class FilterMethod(val code: Byte) {
		ADAPTIVE(0);

		companion object {
			val mapping = entries.associateBy(FilterMethod::code)
		}
	}

	enum class InterlaceMethod(val code: Byte) {
		NO_INTERLACE(0),
		ADAM_7(1);

		companion object {
			val mapping = entries.associateBy(InterlaceMethod::code)
		}
	}

	constructor(from: DataInputStream) : this(
		from.readInt(),
		from.readInt(),
		from.readByte(),
		ColorType.mapping.getValue(from.readByte()),
		CompressionMethod.mapping.getValue(from.readByte()),
		FilterMethod.mapping.getValue(from.readByte()),
		InterlaceMethod.mapping.getValue(from.readByte())
	)

	init {
		if (!colorType.allowedDepths.contains(bitDepth))
			throw RasterImage.ImageParsingException("Bit depth [$bitDepth] is not in allowable range [${colorType.allowedDepths}]")
	}
}