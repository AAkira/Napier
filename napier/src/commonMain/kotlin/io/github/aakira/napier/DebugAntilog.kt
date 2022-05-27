package io.github.aakira.napier

import kotlin.js.JsName
import kotlin.jvm.JvmName

expect class DebugAntilog(
    defaultTag: String = "app",
    logLevelChecker: (LogLevel, String?) -> Boolean
) : Antilog


@JvmName("DebugAntilogFromLogLevels")
@JsName("DebugAntilogFromLogLevels")
fun DebugAntilog(
    defaultTag: String = "app",
    enabledLevels: Array<LogLevel> = LogLevel.values()
) = DebugAntilog(defaultTag) { level, _ -> level in enabledLevels }

fun DebugAntilog(
    defaultTag: String = "app",
    enabledLevels: Iterable<LogLevel>
) = DebugAntilog(defaultTag, enabledLevels.toList().toTypedArray())

fun DebugAntilog(
    defaultTag: String = "app",
    vararg enabledLevels: LogLevel
): DebugAntilog {
    @Suppress("UNCHECKED_CAST")
    return DebugAntilog(defaultTag, enabledLevels as Array<LogLevel>)
}
