package org.bread_experts_group

import org.bread_experts_group.windowing.Window
import java.util.logging.Logger

val mainLogger = Logger.getLogger("Instrumentation Main")

fun main() {
	Window.create()
	Thread.sleep(90000)
}