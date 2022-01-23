package io.github.aakira.napier

import io.github.aakira.napier.atomic.AtomicMutableList

/**
 * ## Logging library for Kotlin Multiplatform
 *
 * [Napier](https://github.com/AAkira/Napier) is a logger library for Kotlin Multiplatform.
 * It supports for the Android, Darwin(iOS, macOS, watchOS, tvOS), JVM, JavaScript.
 * Logs written in common module are displayed on logger viewer of each platform.
 *
 * Generally, you should use the [ Napier.v() ], [Napier.d()], [Napier.i()], [Napier.w()], and
 * [Napier.e()] or [log()] methods to write logs. You can then view the logs in logcat.
 *
 * **Usage :**
 *
 * ```kotlin
 * // You must initialize the Napier in your module.
 * Napier.base(DebugAntilog())
 *
 * // verbose log
 * Napier.v("Hello napier")
 * Napier.v { "Hello napier" }
 *
 * // you can set a tag for each log
 * Napier.d("optional tag", tag = "your tag")
 * Napier.d(tag = "your tag") { "optional tag" }
 *
 * try {
 *     ...
 * } catch (e: Exception) {
 *     // you can set the throwable
 *     Napier.e("Napier Error", e)
 *     Napier.e(e) { "Napier Error" }
 * }
 *
 * // you can also use top-level function
 * log { "top-level" }
 * log(tag = "your tag") { "top-level" }
 * ```
 */
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

    /** Send a VERBOSE log message and log the exception.
     *
     * @param tag String: Used to identify the source of a log message. It usually
     * identifies the class or activity where the log call occurs. This value may be null.
     * @param throwable Throwable: An exception to log This value may be null.
     * @param message String: The message you would like logged. This value cannot be null.
     */
    fun v(message: String, tag: String? = null, throwable: Throwable? = null) {
        log(LogLevel.VERBOSE, tag, throwable, message)
    }

    /** Send a VERBOSE log message and log the exception.
     *
     * @param tag String: Used to identify the source of a log message. It usually
     * identifies the class or activity where the log call occurs. This value may be null.
     * @param throwable Throwable: An exception to log This value may be null.
     * @param message Lambda: The message you would like logged. This value cannot be null.
     */
    inline fun v(tag: String? = null, throwable: Throwable? = null, message: () -> String) {
        log(LogLevel.VERBOSE, tag, throwable, message())
    }

    /** Send a INFO log message and log the exception.
     *
     * @param tag String: Used to identify the source of a log message. It usually
     * identifies the class or activity where the log call occurs. This value may be null.
     * @param throwable Throwable: An exception to log This value may be null.
     * @param message String: The message you would like logged. This value cannot be null.
     */
    fun i(message: String, tag: String? = null, throwable: Throwable? = null) {
        log(LogLevel.INFO, tag, throwable, message)
    }

    /** Send a INFO log message and log the exception.
     *
     * @param tag String: Used to identify the source of a log message. It usually
     * identifies the class or activity where the log call occurs. This value may be null.
     * @param throwable Throwable: An exception to log This value may be null.
     * @param message Lambda: The message you would like logged. This value cannot be null.
     */
    inline fun i(tag: String? = null, throwable: Throwable? = null, message: () -> String) {
        log(LogLevel.INFO, tag, throwable, message())
    }

    /** Send a DEBUG log message and log the exception.
     *
     * @param tag String: Used to identify the source of a log message. It usually
     * identifies the class or activity where the log call occurs. This value may be null.
     * @param throwable Throwable: An exception to log This value may be null.
     * @param message String: The message you would like logged. This value cannot be null.
     */
    fun d(message: String, tag: String? = null, throwable: Throwable? = null) {
        log(LogLevel.DEBUG, tag, throwable, message)
    }

    /** Send a DEBUG log message and log the exception.
     *
     * @param tag String: Used to identify the source of a log message. It usually
     * identifies the class or activity where the log call occurs. This value may be null.
     * @param throwable Throwable: An exception to log This value may be null.
     * @param message Lambda: The message you would like logged. This value cannot be null.
     */
    inline fun d(tag: String? = null, throwable: Throwable? = null, message: () -> String) {
        log(LogLevel.DEBUG, tag, throwable, message())
    }

    /** Send a WARN log message and log the exception.
     *
     * @param tag String: Used to identify the source of a log message. It usually
     * identifies the class or activity where the log call occurs. This value may be null.
     * @param throwable Throwable: An exception to log This value may be null.
     * @param message String: The message you would like logged. This value cannot be null.
     */
    fun w(message: String, tag: String? = null, throwable: Throwable? = null) {
        log(LogLevel.WARNING, tag, throwable, message)
    }

    /** Send a WARN log message and log the exception.
     *
     * @param tag String: Used to identify the source of a log message. It usually
     * identifies the class or activity where the log call occurs. This value may be null.
     * @param throwable Throwable: An exception to log This value may be null.
     * @param message Lambda: The message you would like logged. This value cannot be null.
     */
    inline fun w(tag: String? = null, throwable: Throwable? = null, message: () -> String) {
        log(LogLevel.WARNING, tag, throwable, message())
    }

    /** Send a ERROR log message and log the exception.
     *
     * @param tag String: Used to identify the source of a log message. It usually
     * identifies the class or activity where the log call occurs. This value may be null.
     * @param throwable Throwable: An exception to log This value may be null.
     * @param message String: The message you would like logged. This value cannot be null.
     */
    fun e(message: String, throwable: Throwable? = null, tag: String? = null) {
        log(LogLevel.ERROR, tag, throwable, message)
    }

    /** Send a ERROR log message and log the exception.
     *
     * @param tag String: Used to identify the source of a log message. It usually
     * identifies the class or activity where the log call occurs. This value may be null.
     * @param throwable Throwable: An exception to log This value may be null.
     * @param message Lambda: The message you would like logged. This value cannot be null.
     */
    inline fun e(throwable: Throwable? = null, tag: String? = null, message: () -> String) {
        log(LogLevel.ERROR, tag, throwable, message())
    }

    /** What a Terrible Failure: Report an exception that should never happen.
     *
     * @param tag String: Used to identify the source of a log message. It usually
     * identifies the class or activity where the log call occurs. This value may be null.
     * @param throwable Throwable: An exception to log This value may be null.
     * @param message String: The message you would like logged. This value cannot be null.
     */
    fun wtf(message: String, tag: String? = null, throwable: Throwable? = null) {
        log(LogLevel.ASSERT, tag, throwable, message)
    }

    /** What a Terrible Failure: Report an exception that should never happen.
     *
     * @param tag String: Used to identify the source of a log message. It usually
     * identifies the class or activity where the log call occurs. This value may be null.
     * @param throwable Throwable: An exception to log This value may be null.
     * @param message Lambda: The message you would like logged. This value cannot be null.
     */
    inline fun wtf(tag: String? = null, throwable: Throwable? = null, message: () -> String) {
        log(LogLevel.ASSERT, tag, throwable, message())
    }

    /** Low-level logging call.
     *
     * @param priority enum: The priority/type of this log message Value is ASSERT,
     * ERROR, WARN, INFO, DEBUG, or VERBOSE
     * @param tag String: Used to identify the source of a log message. It usually
     * identifies the class or activity where the log call occurs. This value may be null.
     * @param throwable Throwable: An exception to log This value may be null.
     * @param message String: The message you would like logged. This value cannot be null.
     */
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

    /** Remove antilog from the base array. */
    fun takeLogarithm(antilog: Antilog) {
        baseArray.remove(antilog)
    }

    /** Clear all antilogs from the base array. */
    fun takeLogarithm() {
        baseArray.clear()
    }
}

/** ### Top-level logging [Napier].
 * Example :
 * ```kotlin
 * log { "Hello, Napier" }
 * log(tag = "your tag") { "Hello, Napier" }
 * ```
 *
 * @param priority enum: The priority/type of this log message Value is ASSERT,
 * ERROR, WARN, INFO, DEBUG, or VERBOSE
 * @param tag String: Used to identify the source of a log message. It usually
 * identifies the class or activity where the log call occurs. This value may be null.
 * @param throwable Throwable: An exception to log This value may be null.
 * @param message Lambda: The message you would like logged. This value cannot be null.
 * @see Napier
 * @author Ghasem Shirdel
 */
inline fun log(
    tag: String? = null,
    throwable: Throwable? = null,
    priority: LogLevel = LogLevel.DEBUG,
    message: () -> String,
) {
    Napier.log(priority, tag, throwable, message())
}
