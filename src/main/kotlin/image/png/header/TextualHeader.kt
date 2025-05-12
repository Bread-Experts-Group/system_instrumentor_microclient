package org.bread_experts_group.image.png.header

import org.bread_experts_group.socket.scanDelimiter
import java.io.DataInputStream

data class TextualHeader(
	val keyword: String,
	val text: String
) : PortableNetworkGraphicsHeader("tEXt") {
	constructor(from: DataInputStream) : this(
		from.scanDelimiter("\u0000"),
		from.readBytes().decodeToString()
	)
}