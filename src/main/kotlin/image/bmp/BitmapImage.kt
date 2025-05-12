package org.bread_experts_group.image.bmp

//class BitmapImage(from: DataInputStream) : RasterImage() {
//	init {
//		val newHeaders = mutableListOf<PortableNetworkGraphicsHeader>()
//		if (!from.readNBytes(8).contentEquals(signature))
//			throw ImageParsingException("PNG magic bytes failed verification")
//		var localFormat: ImageFormatHeader? = null
//		var dats = byteArrayOf()
//		while (from.available() > 0) {
//			val length = from.readInt()
//			val name = from.readString(4)
//			if (name == "IDAT") dats += from.readNBytes(length)
//			else {
//				val newHeader = PortableNetworkGraphicsHeader.Companion.read(
//					name,
//					DataInputStream(ByteArrayInputStream(from.readNBytes(length)))
//				)
//				if (newHeader != null) {
//					if (name == "IHDR") localFormat = newHeader as ImageFormatHeader
//					else newHeaders.add(newHeader)
//				}
//			}
//			from.skip(4)
//		}
//		formatHeader = localFormat!!
//		dataHeader = ImageDataHeader(formatHeader, ByteArrayInputStream(dats))
//		headers = newHeaders.toTypedArray()
//		println(formatHeader.width)
//	}
//
//	override val width: Int = formatHeader.width
//	override val height: Int = formatHeader.height
//	override val bitDepth: Byte = formatHeader.bitDepth
//	override val colorType: ImageFormatHeader.ColorType = formatHeader.colorType
//	override val data: Array<Array<Array<Byte>>> = dataHeader.pixels
//}