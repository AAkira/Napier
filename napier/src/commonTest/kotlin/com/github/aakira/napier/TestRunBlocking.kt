package com.github.aakira.napier

expect fun <T> testRunBlocking(block: suspend () -> T)
