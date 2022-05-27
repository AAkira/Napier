package io.github.aakira.napier

actual class DebugAntilog actual constructor(
    private val defaultTag: String,
    private val logLevelChecker: (LogLevel, String?) -> Boolean
) : Antilog() {

    override fun isEnable(priority: LogLevel, tag: String?): Boolean = logLevelChecker(priority, tag)

    override fun performLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?,
    ) {
        val logTag = tag ?: defaultTag

        val fullMessage = if (message != null) {
            if (throwable != null) {
                "$message\n${throwable.message}"
            } else {
                message
            }
        } else throwable?.message ?: return

        when (priority) {
            LogLevel.VERBOSE -> console.log("VERBOSE $logTag : $fullMessage")
            LogLevel.DEBUG -> console.log("DEBUG $logTag : $fullMessage")
            LogLevel.INFO -> console.info("INFO $logTag : $fullMessage")
            LogLevel.WARNING -> console.warn("WARNING $logTag : $fullMessage")
            LogLevel.ERROR -> console.error("ERROR $logTag : $fullMessage")
            LogLevel.ASSERT -> console.error("ASSERT $logTag : $fullMessage")
        }
    }
}
