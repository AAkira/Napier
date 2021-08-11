package io.github.aakira.napier.mppsample

import kotlinx.coroutines.launch

fun Sample.suspendHelloKt() {
    backgroundScope.launch {
        suspendHello()
    }
}
