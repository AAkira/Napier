package com.github.aakira.napier

expect fun <T> runBlocking(block: suspend () -> T)
