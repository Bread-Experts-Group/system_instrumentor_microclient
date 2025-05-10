package org.bread_experts_group

import org.bread_experts_group.graphics.elements.Color
import org.bread_experts_group.graphics.elements.TextLabel
import org.bread_experts_group.windowing.Window
import java.util.logging.Logger
import kotlin.random.Random

val mainLogger: Logger = Logger.getLogger("Instrumentation Main")

fun main() {
	val window = Window.create("ばやちゃお")
	window.show()
	repeat(100) {
		window.graphics.elements.add(
			TextLabel(
				"あああああああああああああああ",
				foregroundColor = Color(Random.nextInt(0, 255), 0, 0),
				x = Random.nextInt(0, 2000),
				y = Random.nextInt(0, 1000)
			)
		)
	}
}