package io.github.aakira.napier

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.coroutines.EmptyCoroutineContext

actual fun <T> testRunBlocking(block: suspend () -> T) {
    GlobalScope.promise(EmptyCoroutineContext) { block() }
}
