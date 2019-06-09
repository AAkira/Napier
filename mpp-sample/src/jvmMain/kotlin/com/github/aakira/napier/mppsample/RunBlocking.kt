package com.github.aakira.napier.mppsample

import kotlinx.coroutines.runBlocking

actual fun <T> runBlocking(block: suspend () -> T) {
    runBlocking { block() }
}
