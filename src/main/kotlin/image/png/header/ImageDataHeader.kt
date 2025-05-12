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
							8 -> arrayOf(realStream.readByte(), realStream.readByte(), realStream.readByte())
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
								localPixel[i] = (b + lastPixel[i]).toByte()
							}
						}

						AdaptiveFilteringMode.AVERAGE -> {
							val topRow = allLines.lastOrNull()
							val topPixel = topRow?.get(scanLine.size) ?: empty
							val leftPixel = scanLine.getOrNull(scanLine.size - 1) ?: empty
							localPixel.forEachIndexed { i, b ->
								localPixel[i] = (b + ((leftPixel[i] + topPixel[i]) / 2)).toByte()
								//    Raw(x-bpp)+Prior(x)
							}
						}

						AdaptiveFilteringMode.PAETH -> {
							val leftPixel = scanLine.getOrNull(scanLine.size - 1) ?: empty
							val lastLine = allLines.lastOrNull()
							val topPixel = lastLine?.get(scanLine.size) ?: empty
							val topLeftPixel = lastLine?.getOrNull(scanLine.size - 1) ?: empty
							localPixel.forEachIndexed { i, b ->
								val estimate = leftPixel[i] + topPixel[i] - topLeftPixel[i]
								val distanceLeft = abs(estimate - leftPixel[i])
								val distanceTop = abs(estimate - topPixel[i])
								val distanceTopLeft = abs(estimate - topLeftPixel[i])
								val predicted = when {
									distanceLeft <= distanceTop && distanceLeft <= distanceTopLeft -> leftPixel
									distanceTop <= distanceTopLeft -> topPixel
									else -> topLeftPixel
								}[i]
								localPixel[i] = (b + predicted).toByte()
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