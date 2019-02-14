package com.github.aakira.napier

fun debugBuild() {
    Napier.base(DebugAntilog())
}

fun releaseBuild(antilog: Antilog) {
    Napier.base(antilog)
}
