package io.github.aakira.napier

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSThread

private const val CALL_STACK_INDEX = 8

actual class DebugAntilog(
    private val defaultTag: String = "app",
    private val coroutinesSuffix: Boolean = true,
) : Antilog() {
    actual constructor(defaultTag: String) : this(defaultTag, coroutinesSuffix = true)

    var crashAssert = false

    private val dateFormatter = NSDateFormatter().apply {
        dateFormat = "MM-dd HH:mm:ss.SSS"
    }

    private val tagMap: HashMap<LogLevel, String> = hashMapOf(
        LogLevel.VERBOSE to "💜 VERBOSE",
        LogLevel.DEBUG to "💚 DEBUG",
        LogLevel.INFO to "💙 INFO",
        LogLevel.WARNING to "💛 WARN",
        LogLevel.ERROR to "❤️ ERROR",
        LogLevel.ASSERT to "💞 ASSERT"
    )

    override fun performLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?,
        callerInfo: CallerInfo,
    ) {
        if (priority == LogLevel.ASSERT) {
            assert(crashAssert) { buildLog(priority, tag, message, callerInfo) }
        } else {
            println(buildLog(priority, tag, message, callerInfo))
        }
    }

    fun setTag(level: LogLevel, tag: String) {
        tagMap[level] = tag
    }

    fun setDateFormatterString(formatter: String) {
        dateFormatter.dateFormat = formatter
    }

    private fun getCurrentTime() = dateFormatter.stringFromDate(NSDate())

    private fun buildLog(priority: LogLevel, tag: String?, message: String?, callerInfo: CallerInfo): String {
        return "${getCurrentTime()} ${tagMap[priority]} ${tag ?: performTag(defaultTag, callerInfo)} - $message"
    }

    // find stack trace
    private fun performTag(tag: String, callerInfo: CallerInfo): String {
        return when (callerInfo) {
            is CallerInfo.DetectByStackTrace -> {
                val thread = NSThread.callStackSymbols

                if (thread.size >= CALL_STACK_INDEX) {
                    createStackElementTag(thread[CALL_STACK_INDEX] as String)
                } else {
                    tag
                }
            }
            is CallerInfo.Exact -> {
                callerInfo.toString()
            }
        }
    }

    internal fun createStackElementTag(string: String): String {
        var tag = string
        tag = tag.substringBeforeLast('$')
        tag = tag.substringBeforeLast('(')
        if (tag.contains("$")) {
            // coroutines
            tag = tag.substring(tag.lastIndexOf(".", tag.lastIndexOf(".") - 1) + 1)
            tag = tag.replace("$", "")
            tag = tag.replace("COROUTINE", if (coroutinesSuffix) "[async]" else "")
        } else {
            // others
            tag = tag.substringAfterLast(".")
            tag = tag.replace("#", ".")
        }
        return tag
    }
}
