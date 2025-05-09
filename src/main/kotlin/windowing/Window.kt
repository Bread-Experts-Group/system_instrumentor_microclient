package org.bread_experts_group.windowing

import java.lang.AutoCloseable

abstract class Window : AutoCloseable {
	companion object {
		fun create(): Window = when (val system = System.getProperty("os.name")) {
			"Windows 11" -> Win32Window()
			else -> throw UnsupportedOperationException("Unsupported system [$system]")
		}
	}
}