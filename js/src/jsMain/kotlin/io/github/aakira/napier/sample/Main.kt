package io.github.aakira.napier.sample

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.github.aakira.napier.mppsample.Sample
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun main() {
    Napier.base(DebugAntilog("napier js"))

    Sample().hello()

    GlobalScope.launch {
        Sample().suspendHello()
    }

    Sample().handleError()
}
