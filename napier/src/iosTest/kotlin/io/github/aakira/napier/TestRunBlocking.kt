package io.github.aakira.napier

import kotlinx.coroutines.runBlocking

actual fun <T> testRunBlocking(block: suspend () -> T) {
    runBlocking { block() }
}
