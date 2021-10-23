package io.github.aakira.napier

actual class DebugAntilog actual constructor(private val defaultTag: String) : Antilog() {

    override fun performLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?,
        callerInfo: CallerInfo,
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
