package io.github.aakira.napier

abstract class Antilog {

    open fun isEnable(priority: LogLevel, tag: String?) = true

    fun log(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?,
        callerInfo: CallerInfo,
    ) {
        if (isEnable(priority, tag)) {
            performLog(priority, tag, throwable, message, callerInfo)
        }
    }

    internal fun rawLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?,
        callerInfo: CallerInfo,
    ) {
        performLog(priority, tag, throwable, message, callerInfo)
    }

    protected abstract fun performLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?,
        callerInfo: CallerInfo,
    )
}
