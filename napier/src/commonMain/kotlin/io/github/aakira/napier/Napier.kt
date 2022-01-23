package io.github.aakira.napier

import io.github.aakira.napier.atomic.AtomicMutableList

object Napier {

    enum class Level {
        VERBOSE,
        DEBUG,
        INFO,
        WARNING,
        ERROR,
        ASSERT,
    }

    private val baseArray = AtomicMutableList<Antilog>()

    fun base(antilog: Antilog) {
        baseArray.add(antilog)
    }

    fun isEnable(priority: LogLevel, tag: String?) = baseArray.any { it.isEnable(priority, tag) }

    @PublishedApi
    internal fun rawLog(priority: LogLevel, tag: String?, throwable: Throwable?, message: String?) {
        baseArray.forEach { it.rawLog(priority, tag, throwable, message) }
    }

    fun v(message: String, tag: String? = null, throwable: Throwable? = null) {
        log(LogLevel.VERBOSE, tag, throwable, message)
    }

    inline fun v(tag: String? = null, throwable: Throwable? = null, message: () -> String) {
        log(LogLevel.VERBOSE, tag, throwable, message())
    }

    fun i(message: String, tag: String? = null, throwable: Throwable? = null) {
        log(LogLevel.INFO, tag, throwable, message)
    }

    inline fun i(tag: String? = null, throwable: Throwable? = null, message: () -> String) {
        log(LogLevel.INFO, tag, throwable, message())
    }

    fun d(message: String, tag: String? = null, throwable: Throwable? = null) {
        log(LogLevel.DEBUG, tag, throwable, message)
    }

    inline fun d(tag: String? = null, throwable: Throwable? = null, message: () -> String) {
        log(LogLevel.DEBUG, tag, throwable, message())
    }

    fun w(message: String, tag: String? = null, throwable: Throwable? = null) {
        log(LogLevel.WARNING, tag, throwable, message)
    }

    inline fun w(tag: String? = null, throwable: Throwable? = null, message: () -> String) {
        log(LogLevel.WARNING, tag, throwable, message())
    }

    fun e(message: String, throwable: Throwable? = null, tag: String? = null) {
        log(LogLevel.ERROR, tag, throwable, message)
    }

    inline fun e(throwable: Throwable? = null, tag: String? = null, message: () -> String) {
        log(LogLevel.ERROR, tag, throwable, message())
    }

    fun wtf(message: String, tag: String? = null, throwable: Throwable? = null) {
        log(LogLevel.ASSERT, tag, throwable, message)
    }

    inline fun wtf(tag: String? = null, throwable: Throwable? = null, message: () -> String) {
        log(LogLevel.ASSERT, tag, throwable, message())
    }

    fun log(
        priority: LogLevel,
        tag: String? = null,
        throwable: Throwable? = null,
        message: String
    ) {
        if (Napier.isEnable(priority, tag)) {
            Napier.rawLog(priority, tag, throwable, message)
        }
    }

    /**
     * Remove antilog from the base array.
     */
    fun takeLogarithm(antilog: Antilog) {
        baseArray.remove(antilog)
    }

    /**
     * Clear all antilogs from the base array.
     */
    fun takeLogarithm() {
        baseArray.clear()
    }
}

inline fun log(
    tag: String? = null,
    throwable: Throwable? = null,
    priority: LogLevel = LogLevel.DEBUG,
    message: () -> String,
) {
    Napier.log(priority, tag, throwable, message())
}
