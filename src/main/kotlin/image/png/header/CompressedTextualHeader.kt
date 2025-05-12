package org.bread_experts_group.image.png.header

import org.bread_experts_group.socket.scanDelimiter
import java.io.DataInputStream
import java.util.zip.InflaterInputStream

data class CompressedTextualHeader(
	val keyword: String,
	val compressionMethod: ImageFormatHeader.CompressionMethod,
	val text: String
) : PortableNetworkGraphicsHeader("zTXt") {
	private constructor(from: DataInputStream, keyword: String, method: ImageFormatHeader.CompressionMethod) : this(
		keyword,
		method,
		when (method) {
			ImageFormatHeader.CompressionMethod.DEFLATE -> InflaterInputStream(from).use {
				it.readBytes().decodeToString()
			}
		}
	)

	constructor(from: DataInputStream) : this(
		from,
		from.scanDelimiter("\u0000"),
		ImageFormatHeader.CompressionMethod.mapping.getValue(from.readByte())
	)
}