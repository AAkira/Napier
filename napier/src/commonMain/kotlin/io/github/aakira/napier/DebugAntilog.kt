package io.github.aakira.napier

expect class DebugAntilog(defaultTag: String = "app") : Antilog {
    override fun performLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?,
    )
}