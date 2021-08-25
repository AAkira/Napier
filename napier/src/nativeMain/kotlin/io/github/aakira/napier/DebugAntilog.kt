package io.github.aakira.napier

actual class DebugAntilog actual constructor(private val defaultTag: String) : Antilog() {

    var crashAssert = false

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
    ) {
        if (priority == LogLevel.ASSERT) {
            assert(crashAssert) { buildLog(priority, tag, message) }
        } else {
            println(buildLog(priority, tag, message))
        }
    }

    fun setTag(level: LogLevel, tag: String) {
        tagMap[level] = tag
    }

    private fun buildLog(priority: LogLevel, tag: String?, message: String?): String {
        return "todo time ${tagMap[priority]} ${tag ?: performTag(defaultTag)} - $message"
    }

    // find stack trace
    private fun performTag(tag: String): String {
        return "todo"
    }
}
