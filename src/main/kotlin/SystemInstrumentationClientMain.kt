package org.bread_experts_group

import org.bread_experts_group.graphics.elements.Color
import org.bread_experts_group.graphics.elements.RasterImageElement
import org.bread_experts_group.graphics.elements.TextLabelElement
import org.bread_experts_group.image.png.PortableNetworkGraphics
import org.bread_experts_group.windowing.Window
import java.io.DataInputStream
import java.io.FileInputStream
import kotlin.random.Random

fun main() {
	val imageFile = PortableNetworkGraphics(DataInputStream(FileInputStream("img_1.png")))
	val window = Window.create("ばやちゃお")
	repeat(100) {
		window.graphics.elements.add(
			TextLabelElement(
				"あああああああああああああああ",
				foregroundColor = Color(Random.nextInt(0, 255), 0, 0),
				x = Random.nextInt(0, 2000),
				y = Random.nextInt(0, 1000)
			)
		)
	}
	window.graphics.elements.add(RasterImageElement(imageFile))
	window.show()
}