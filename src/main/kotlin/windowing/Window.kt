package org.bread_experts_group.windowing

import org.bread_experts_group.graphics.Graphics
import java.io.IOException
import java.lang.AutoCloseable
import java.util.logging.Logger

sealed class Window : AutoCloseable {
	abstract var title: String
	abstract val graphics: Graphics

	protected val logger: Logger = Logger.getLogger("Window")
	protected var closed: Boolean = false
	fun ensureOpen() = if (closed) throw WindowClosedException() else {
	}

	class WindowClosedException : IOException()
	class WindowOperationException : IOException()

	abstract fun show()
	abstract fun hide()
	abstract fun minimize()
	abstract fun maximize()
	abstract fun restore()

	companion object {
		fun create(
			title: String = "BSL Window"
		): Window = when (val system = System.getProperty("os.name")) {
			"Windows 11" -> Win32Window(title)
			else -> throw UnsupportedOperationException("Unsupported system [$system]")
		}
	}
}