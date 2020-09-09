package com.github.aakira.napier

abstract class Antilog {

    open fun isEnable(priority: LogLevel, tag: String?) = true

    fun log(priority: LogLevel, tag: String?, throwable: Throwable?, message: String?) {
        if (isEnable(priority, tag)) {
            performLog(priority, tag, throwable, message)
        }
    }

    internal fun rawLog(priority: LogLevel, tag: String?, throwable: Throwable?, message: String?) {
        performLog(priority, tag, throwable, message)
    }

    protected abstract fun performLog(priority: LogLevel, tag: String?, throwable: Throwable?, message: String?)
}
