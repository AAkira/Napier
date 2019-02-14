package com.github.aakira.napier

abstract class Antilog {

    open fun isEnable(priority: Napier.Level, tag: String?) = true

    fun log(priority: Napier.Level, tag: String?, throwable: Throwable?, message: String?) {
        if (isEnable(priority, tag)) {
            performLog(priority, tag, throwable, message)
        }
    }

    internal fun rawLog(priority: Napier.Level, tag: String?, throwable: Throwable?, message: String?) {
        performLog(priority, tag, throwable, message)
    }

    protected abstract fun performLog(priority: Napier.Level, tag: String?, throwable: Throwable?, message: String?)
}
