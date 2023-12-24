package io.github.aakira.napier


private fun log(message: String): Unit = js(
    """{
        console.log(message);
    }"""
)

private fun info(message: String): Unit = js(
    """{
        console.info(message);
    }"""
)

private fun warn(message: String): Unit = js(
    """{
        console.warn(message);
    }"""
)
private fun error(message: String): Unit = js(
    """{
        console.error(message);
    }"""
)

actual class DebugAntilog actual constructor(private val defaultTag: String) : Antilog() {

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
            LogLevel.VERBOSE -> log("VERBOSE $logTag : $fullMessage")
            LogLevel.DEBUG -> log("DEBUG $logTag : $fullMessage")
            LogLevel.INFO -> info("INFO $logTag : $fullMessage")
            LogLevel.WARNING -> warn("WARNING $logTag : $fullMessage")
            LogLevel.ERROR -> error("ERROR $logTag : $fullMessage")
            LogLevel.ASSERT -> error("ASSERT $logTag : $fullMessage")
        }
    }
}
