package io.github.aakira.napier.mppsample

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

internal val mainScope = SharedScope(Dispatchers.Main)

internal val backgroundScope = SharedScope(Dispatchers.Default)

internal class SharedScope(private val context: CoroutineContext) : CoroutineScope {
    private val job = Job()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("[Coroutine Exception] $throwable")
    }

    override val coroutineContext: CoroutineContext
        get() = context + job + exceptionHandler
}
