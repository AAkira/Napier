package io.github.aakira.napier.mppsample

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.coroutines.EmptyCoroutineContext

actual fun <T> runBlocking(block: suspend () -> T) {
    GlobalScope.promise(EmptyCoroutineContext) { block() }
}
