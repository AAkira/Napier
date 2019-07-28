package com.github.aakira.napier

import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object Napier {

    enum class Level {
        VERBOSE,
        DEBUG,
        INFO,
        WARNING,
        ERROR,
        ASSERT,
    }

    private val baseArray = mutableListOf<Antilog>()

    fun base(antilog: Antilog) {
        baseArray.add(antilog)
    }

    fun isEnable(priority: Level, tag: String?) = baseArray.any { it.isEnable(priority, tag) }

    @PublishedApi
    internal fun rawLog(priority: Level, tag: String?, throwable: Throwable?, message: String?) {
        baseArray.forEach { it.rawLog(priority, tag, throwable, message) }
    }

    fun v(message: String, throwable: Throwable? = null, tag: String? = null) {
        log(Level.VERBOSE, tag, throwable, message)
    }

    fun v(message: () -> String, throwable: Throwable? = null, tag: String? = null) {
        log(Level.VERBOSE, tag, throwable, message)
    }

    fun i(message: String, throwable: Throwable? = null, tag: String? = null) {
        log(Level.INFO, tag, throwable, message)
    }

    fun i(message: () -> String, throwable: Throwable? = null, tag: String? = null) {
        log(Level.INFO, tag, throwable, message)
    }

    fun d(message: String, throwable: Throwable? = null, tag: String? = null) {
        log(Level.DEBUG, tag, throwable, message)
    }

    fun d(message: () -> String, throwable: Throwable? = null, tag: String? = null) {
        log(Level.DEBUG, tag, throwable, message)
    }

    fun w(message: String, throwable: Throwable? = null, tag: String? = null) {
        log(Level.WARNING, tag, throwable, message)
    }

    fun w(message: () -> String, throwable: Throwable? = null, tag: String? = null) {
        log(Level.WARNING, tag, throwable, message)
    }

    fun e(message: String, throwable: Throwable? = null, tag: String? = null) {
        log(Level.ERROR, tag, throwable, message)
    }

    fun e(message: () -> String, throwable: Throwable? = null, tag: String? = null) {
        log(Level.ERROR, tag, throwable, message)
    }

    fun wtf(message: String, throwable: Throwable? = null, tag: String? = null) {
        log(Level.ASSERT, tag, throwable, message)
    }

    fun wtf(message: () -> String, throwable: Throwable? = null, tag: String? = null) {
        log(Level.ASSERT, tag, throwable, message)
    }

    fun log(priority: Level, tag: String? = null, throwable: Throwable? = null, message: String) {
        if (isEnable(priority, tag)) {
            rawLog(priority, tag, throwable, message)
        }
    }

    fun log(priority: Level, tag: String? = null, throwable: Throwable? = null, message: () -> String) {
        if (isEnable(priority, tag)) {
            rawLog(priority, tag, throwable, message())
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
