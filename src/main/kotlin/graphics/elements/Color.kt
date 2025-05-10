package org.bread_experts_group.graphics.elements

import kotlin.math.roundToInt

data class Color(
	val r: UByte,
	val g: UByte,
	val b: UByte
) {
	val rawBGR = (b.toInt() shl 16) or (g.toInt() shl 8) or r.toInt()

	constructor(
		r: Int,
		g: Int,
		b: Int
	) : this(r.toUByte(), g.toUByte(), b.toUByte())

	constructor(
		r: Double,
		g: Double,
		b: Double
	) : this((r * 255).roundToInt(), (g * 255).roundToInt(), (b * 255).roundToInt())

	constructor(
		r: Float,
		g: Float,
		b: Float
	) : this(r.toDouble(), g.toDouble(), b.toDouble())
}