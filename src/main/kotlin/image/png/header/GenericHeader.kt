package org.bread_experts_group.image.png.header

import java.io.DataInputStream

data class GenericHeader(
	override val name: String,
	val stream: DataInputStream
) : PortableNetworkGraphicsHeader(name)