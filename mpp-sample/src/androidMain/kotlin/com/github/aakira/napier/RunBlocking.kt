package com.github.aakira.napier

import kotlinx.coroutines.runBlocking

actual fun <T> runBlocking(block: suspend () -> T) {
    runBlocking { block() }
}
