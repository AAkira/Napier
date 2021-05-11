package io.github.aakira.napier.mppsample

expect fun <T> runBlocking(block: suspend () -> T)
