package com.github.aakira.napier

import android.os.Build
import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter
import java.util.regex.Pattern

class DebugAntilog(private val defaultTag: String = "app") : Antilog() {

    companion object {
        private const val MAX_LOG_LENGTH = 4000
        private const val MAX_TAG_LENGTH = 23
        private const val CALL_STACK_INDEX = 9
    }

    private val anonymousClass = Pattern.compile("(\\$\\d+)+$")

    override fun performLog(priority: LogLevel, tag: String?, throwable: Throwable?, message: String?) {

        val debugTag = tag ?: performTag(defaultTag)

        val fullMessage = if (message != null) {
            if (throwable != null) {
                "$message\n${throwable.stackTraceString}"
            } else {
                message
            }
        } else throwable?.stackTraceString ?: return

        val length = fullMessage.length
        if (length <= MAX_LOG_LENGTH) {
            // Fast path for small messages which can fit in a single call.
            if (priority == LogLevel.ASSERT) {
                Log.wtf(debugTag, fullMessage)
            } else {
                Log.println(priority.toValue(), debugTag, fullMessage)
            }
            return
        }

        // Slow path: Split by line, then ensure each line can fit into Log's maximum length.
        // TODO use lastIndexOf instead of indexOf to batch multiple lines into single calls.
        var i = 0
        while (i < length) {
            var newline = fullMessage.indexOf('\n', i)
            newline = if (newline != -1) newline else length
            do {
                val end = Math.min(newline, i + MAX_LOG_LENGTH)
                val part = fullMessage.substring(i, end)
                if (priority.toValue() == Log.ASSERT) {
                    Log.wtf(debugTag, part)
                } else {
                    Log.println(priority.toValue(), debugTag, part)
                }
                i = end
            } while (i < newline)
            i++
        }
    }

    private fun performTag(tag: String): String {
        val thread = Thread.currentThread().stackTrace

        return if (thread != null && thread.size >= CALL_STACK_INDEX) {
            thread[CALL_STACK_INDEX].run {
                "${createStackElementTag(className)}\$$methodName"
            }
        } else {
            tag
        }
    }

    internal fun createStackElementTag(className: String): String {
        var tag = className
        val m = anonymousClass.matcher(tag)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        tag = tag.substring(tag.lastIndexOf('.') + 1)
        // Tag length limit was removed in API 24.
        return if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tag
        } else tag.substring(0, MAX_TAG_LENGTH)
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

    private fun LogLevel.toValue() = when (this) {
        LogLevel.VERBOSE -> Log.VERBOSE
        LogLevel.DEBUG -> Log.DEBUG
        LogLevel.INFO -> Log.INFO
        LogLevel.WARNING -> Log.WARN
        LogLevel.ERROR -> Log.ERROR
        LogLevel.ASSERT -> Log.ASSERT
    }
}
