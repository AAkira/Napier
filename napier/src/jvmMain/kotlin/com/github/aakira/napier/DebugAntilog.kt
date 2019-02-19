package com.github.aakira.napier

import java.io.PrintWriter
import java.io.StringWriter
import java.util.logging.ConsoleHandler
import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.Logger
import java.util.logging.SimpleFormatter
import java.util.regex.Pattern

class DebugAntilog(
    private val defaultTag: String = "app",
    private val handler: List<Handler> = listOf()
) : Antilog() {

    companion object {
        private const val CALL_STACK_INDEX = 8
    }

    val consoleHandler: ConsoleHandler = ConsoleHandler().apply {
        level = Level.ALL
        formatter = SimpleFormatter()
    }

    private val logger: Logger = Logger.getLogger(DebugAntilog::class.java.name).apply {
        level = Level.ALL

        if (handler.isEmpty()) {
            addHandler(consoleHandler)
            return@apply
        }
        handler.forEach {
            addHandler(it)
        }
    }

    private val anonymousClass = Pattern.compile("(\\$\\d+)+$")

    private val tagMap: HashMap<Napier.Level, String> = hashMapOf(
        Napier.Level.VERBOSE to "[VERBOSE]",
        Napier.Level.DEBUG to "[DEBUG]",
        Napier.Level.INFO to "[INFO]",
        Napier.Level.WARNING to "[WARN]",
        Napier.Level.ERROR to "[ERROR]",
        Napier.Level.ASSERT to "[ASSERT]"
    )

    override fun performLog(priority: Napier.Level, tag: String?, throwable: Throwable?, message: String?) {

        val debugTag = tag ?: performTag(defaultTag)

        val fullMessage = if (message != null) {
            if (throwable != null) {
                "$message\n${throwable.stackTraceString}"
            } else {
                message
            }
        } else throwable?.stackTraceString ?: return

        when (priority) {
            Napier.Level.VERBOSE -> logger.finest(buildLog(priority, debugTag, fullMessage))
            Napier.Level.DEBUG -> logger.fine(buildLog(priority, debugTag, fullMessage))
            Napier.Level.INFO -> logger.info(buildLog(priority, debugTag, fullMessage))
            Napier.Level.WARNING -> logger.warning(buildLog(priority, debugTag, fullMessage))
            Napier.Level.ERROR -> logger.severe(buildLog(priority, debugTag, fullMessage))
            Napier.Level.ASSERT -> logger.severe(buildLog(priority, debugTag, fullMessage))
        }
    }

    private fun buildLog(priority: Napier.Level, tag: String?, message: String?): String {
        return "${tagMap[priority]} ${tag ?: performTag(defaultTag)} - $message"
    }

    private fun performTag(defaultTag: String): String {
        val thread = Thread.currentThread().stackTrace

        return if (thread != null && thread.size >= CALL_STACK_INDEX) {
            thread[CALL_STACK_INDEX].run {
                "${createStackElementTag(className)}\$$methodName"
            }
        } else {
            defaultTag
        }
    }

    private fun createStackElementTag(className: String): String {
        var tag = className
        val m = anonymousClass.matcher(tag)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        return tag.substring(tag.lastIndexOf('.') + 1)
    }

    private val Throwable.stackTraceString
        get(): String {
            // DO NOT replace this with Log.getStackTraceString() - it hides UnknownHostException, which is
            // not what we want.
            val sw = StringWriter(256)
            val pw = PrintWriter(sw, false)
            printStackTrace(pw)
            pw.flush()
            return sw.toString()
        }
}
