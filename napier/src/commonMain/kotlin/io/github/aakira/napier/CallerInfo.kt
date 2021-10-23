package io.github.aakira.napier

sealed class CallerInfo {
    object DetectByStackTrace: CallerInfo()
    data class Exact(val fileName: String, val functionName: String) : CallerInfo() {
        override fun toString() = "$fileName:$functionName"
    }
}