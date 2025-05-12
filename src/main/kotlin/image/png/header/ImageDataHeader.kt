package org.bread_experts_group.image.png.header

import java.io.DataInputStream
import java.io.InputStream
import java.util.zip.InflaterInputStream
import kotlin.math.abs

class ImageDataHeader(
	iHDR: ImageFormatHeader,
	iDAT: InputStream
) : PortableNetworkGraphicsHeader("IDAT") {
	enum class AdaptiveFilteringMode(val code: Byte) {
		NONE(0),
		SUB(1),
		UP(2),
		AVERAGE(3),
		PAETH(4);

		companion object {
			val mapping = entries.associateBy(AdaptiveFilteringMode::code)
		}
	}

	val pixels: Array<Array<Array<Byte>>>

	private fun paethPredictor(a: Int, b: Int, c: Int): Int {
		val p = a + b - c
		val pa = abs(p - a)
		val pb = abs(p - b)
		val pc = abs(p - c)
		return when {
			pa <= pb && pa <= pc -> a
			pb <= pc -> b
			else -> c
		}
	}

	init {
		if (iHDR.interlaceMethod != ImageFormatHeader.InterlaceMethod.NO_INTERLACE)
			throw UnsupportedOperationException()

		val realStream = DataInputStream(
			when (iHDR.compressionMethod) {
				ImageFormatHeader.CompressionMethod.DEFLATE -> InflaterInputStream(iDAT)
			}
		)
		val allLines = mutableListOf<Array<Array<Byte>>>()
		val scanLine = mutableListOf<Array<Byte>>()
		val empty: Array<Byte> = arrayOf(0, 0, 0, 0)
		while (realStream.available() > 0) when (iHDR.filterMethod) {
			ImageFormatHeader.FilterMethod.ADAPTIVE -> {
				val localFilter = AdaptiveFilteringMode.mapping.getValue(realStream.readByte())
				repeat(iHDR.width) {
					val localPixel = when (iHDR.colorType) {
						ImageFormatHeader.ColorType.RGB_TRIPLET -> when (iHDR.bitDepth.toInt()) {
							8 -> arrayOf(
								realStream.readByte(),
								realStream.readByte(),
								realStream.readByte()
							)

							else -> throw UnsupportedOperationException(iHDR.bitDepth.toString())
						}

						ImageFormatHeader.ColorType.RGB_TRIPLET_WITH_ALPHA -> when (iHDR.bitDepth.toInt()) {
							8 -> arrayOf(
								realStream.readByte(),
								realStream.readByte(),
								realStream.readByte(),
								realStream.readByte()
							)

							else -> throw UnsupportedOperationException(iHDR.bitDepth.toString())
						}

						ImageFormatHeader.ColorType.GRAYSCALE_WITH_ALPHA -> when (iHDR.bitDepth.toInt()) {
							8 -> arrayOf(
								realStream.readByte(),
								realStream.readByte()
							)

							else -> throw UnsupportedOperationException(iHDR.bitDepth.toString())
						}

						else -> throw UnsupportedOperationException(iHDR.colorType.name)
					}
					when (localFilter) {
						AdaptiveFilteringMode.NONE -> {}
						AdaptiveFilteringMode.SUB -> if (scanLine.isNotEmpty()) {
							val lastPixel = scanLine[scanLine.size - 1]
							localPixel.forEachIndexed { i, b ->
								localPixel[i] = (b + lastPixel[i]).toByte()
							}
						}

						AdaptiveFilteringMode.UP -> {
							val topRow = allLines.lastOrNull()
							val lastPixel = topRow?.get(scanLine.size) ?: empty
							localPixel.forEachIndexed { i, b ->
								localPixel[i] = ((b.toInt() and 0xFF) + (lastPixel[i].toInt() and 0xFF)).toByte()
							}
						}

						AdaptiveFilteringMode.AVERAGE -> {
							val topRow = allLines.lastOrNull()
							val topPixel = topRow?.get(scanLine.size) ?: empty
							val leftPixel = scanLine.getOrNull(scanLine.size - 1) ?: empty
							localPixel.forEachIndexed { i, b ->
								val left = leftPixel[i].toInt() and 0xFF
								val top = topPixel[i].toInt() and 0xFF
								val avg = (left + top) / 2
								val raw = b.toInt() and 0xFF
								localPixel[i] = (raw + avg).toByte()
							}
						}

						AdaptiveFilteringMode.PAETH -> {
							val leftPixel = scanLine.getOrNull(scanLine.size - 1) ?: empty
							val lastLine = allLines.lastOrNull()
							val topPixel = lastLine?.get(scanLine.size) ?: empty
							val topLeftPixel = lastLine?.getOrNull(scanLine.size - 1) ?: empty
							localPixel.forEachIndexed { i, b ->
								val a = leftPixel[i].toInt() and 0xFF
								val bVal = topPixel[i].toInt() and 0xFF
								val c = topLeftPixel[i].toInt() and 0xFF
								val paeth = paethPredictor(a, bVal, c)
								localPixel[i] = ((b.toInt() and 0xFF) + paeth).toByte()
							}
						}
					}
					scanLine.add(localPixel)
				}
				allLines.add(scanLine.toTypedArray())
				scanLine.clear()
			}
		}
		pixels = allLines.toTypedArray()
	}
}