package io.github.aakira.napier

import io.github.aakira.napier.Napier.isEnable
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
    internal fun rawLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?,
        callerInfo: CallerInfo,
    ) {
        baseArray.forEach { it.rawLog(priority, tag, throwable, message, callerInfo) }
    }

    fun v(
        message: String,
        throwable: Throwable? = null,
        tag: String? = null,
        callerInfo: CallerInfo = CallerInfo.DetectByStackTrace,
    ) {
        log(LogLevel.VERBOSE, tag, throwable, message, callerInfo)
    }

    fun v(
        message: () -> String,
        throwable: Throwable? = null,
        tag: String? = null,
        callerInfo: CallerInfo = CallerInfo.DetectByStackTrace,
    ) {
        log(LogLevel.VERBOSE, tag, throwable, message, callerInfo)
    }

    fun i(
        message: String,
        throwable: Throwable? = null,
        tag: String? = null,
        callerInfo: CallerInfo = CallerInfo.DetectByStackTrace,
    ) {
        log(LogLevel.INFO, tag, throwable, message, callerInfo)
    }

    fun i(
        message: () -> String,
        throwable: Throwable? = null,
        tag: String? = null,
        callerInfo: CallerInfo = CallerInfo.DetectByStackTrace,
    ) {
        log(LogLevel.INFO, tag, throwable, message, callerInfo)
    }

    fun d(
        message: String,
        throwable: Throwable? = null,
        tag: String? = null,
        callerInfo: CallerInfo = CallerInfo.DetectByStackTrace,
    ) {
        log(LogLevel.DEBUG, tag, throwable, message, callerInfo)
    }

    fun d(
        message: () -> String,
        throwable: Throwable? = null,
        tag: String? = null,
        callerInfo: CallerInfo = CallerInfo.DetectByStackTrace,
    ) {
        log(LogLevel.DEBUG, tag, throwable, message, callerInfo)
    }

    fun w(
        message: String,
        throwable: Throwable? = null,
        tag: String? = null,
        callerInfo: CallerInfo = CallerInfo.DetectByStackTrace,
    ) {
        log(LogLevel.WARNING, tag, throwable, message, callerInfo)
    }

    fun w(
        message: () -> String,
        throwable: Throwable? = null,
        tag: String? = null,
        callerInfo: CallerInfo = CallerInfo.DetectByStackTrace,
    ) {
        log(LogLevel.WARNING, tag, throwable, message, callerInfo)
    }

    fun e(
        message: String,
        throwable: Throwable? = null,
        tag: String? = null,
        callerInfo: CallerInfo = CallerInfo.DetectByStackTrace,
    ) {
        log(LogLevel.ERROR, tag, throwable, message, callerInfo)
    }

    fun e(
        message: () -> String,
        throwable: Throwable? = null,
        tag: String? = null,
        callerInfo: CallerInfo = CallerInfo.DetectByStackTrace,
    ) {
        log(LogLevel.ERROR, tag, throwable, message, callerInfo)
    }

    fun wtf(
        message: String,
        throwable: Throwable? = null,
        tag: String? = null,
        callerInfo: CallerInfo = CallerInfo.DetectByStackTrace,
    ) {
        log(LogLevel.ASSERT, tag, throwable, message, callerInfo)
    }

    fun wtf(
        message: () -> String,
        throwable: Throwable? = null,
        tag: String? = null,
        callerInfo: CallerInfo = CallerInfo.DetectByStackTrace,
    ) {
        log(LogLevel.ASSERT, tag, throwable, message, callerInfo)
    }

    fun log(
        priority: LogLevel,
        tag: String? = null,
        throwable: Throwable? = null,
        message: String,
        callerInfo: CallerInfo = CallerInfo.DetectByStackTrace,
    ) {
        if (isEnable(priority, tag)) {
            rawLog(priority, tag, throwable, message, callerInfo)
        }
    }

    fun log(
        priority: LogLevel,
        tag: String? = null,
        throwable: Throwable? = null,
        message: () -> String,
        callerInfo: CallerInfo = CallerInfo.DetectByStackTrace,
    ) {
        if (isEnable(priority, tag)) {
            rawLog(priority, tag, throwable, message(), callerInfo)
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
