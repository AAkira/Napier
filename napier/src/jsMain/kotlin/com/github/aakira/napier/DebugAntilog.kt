package com.github.aakira.napier

class DebugAntilog(private val defaultTag: String = "app") : Antilog() {

    override fun performLog(priority: Napier.Level, tag: String?, throwable: Throwable?, message: String?) {
        val logTag = tag ?: defaultTag

        val fullMessage = if (message != null) {
            if (throwable != null) {
                "$message\n${throwable.message}"
            } else {
                message
            }
        } else throwable?.message ?: return

        when (priority) {
            Napier.Level.VERBOSE -> console.log("VERBOSE $logTag : $fullMessage")
            Napier.Level.DEBUG -> console.log("DEBUG $logTag : $fullMessage")
            Napier.Level.INFO -> console.info("INFO $logTag : $fullMessage")
            Napier.Level.WARNING -> console.warn("WARNING $logTag : $fullMessage")
            Napier.Level.ERROR -> console.error("ERROR $logTag : $fullMessage")
            Napier.Level.ASSERT -> console.error("ASSERT $logTag : $fullMessage")
        }
    }
}
