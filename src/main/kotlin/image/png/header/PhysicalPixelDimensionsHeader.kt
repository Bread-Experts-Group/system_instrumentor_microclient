package org.bread_experts_group.image.png.header

import java.io.DataInputStream

data class PhysicalPixelDimensionsHeader(
	val pixelsPerUnitX: Int,
	val pixelsPerUnitY: Int,
	val unit: PixelPhysicalUnit
) : PortableNetworkGraphicsHeader("pHYs") {
	enum class PixelPhysicalUnit(val code: Byte) {
		UNKNOWN(0),
		METER(1);

		companion object {
			val mapping = entries.associateBy(PixelPhysicalUnit::code)
		}
	}

	constructor(from: DataInputStream) : this(
		from.readInt(),
		from.readInt(),
		PixelPhysicalUnit.mapping.getValue(from.readByte())
	)
}