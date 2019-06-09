package com.github.aakira.napier.mppsample

import com.github.aakira.napier.Antilog
import com.github.aakira.napier.DebugAntilog
import com.github.aakira.napier.Napier

fun debugBuild() {
    Napier.base(DebugAntilog())
}

fun releaseBuild(antilog: Antilog) {
    Napier.base(antilog)
}
