package org.bread_experts_group

import org.bread_experts_group.windowing.Window
import java.util.logging.Logger

val mainLogger: Logger = Logger.getLogger("Instrumentation Main")

fun main() {
	val window = Window.create("ばやちゃお")
	window.graphics
	window.show()
	Thread.sleep(15000)
	window.close()
}