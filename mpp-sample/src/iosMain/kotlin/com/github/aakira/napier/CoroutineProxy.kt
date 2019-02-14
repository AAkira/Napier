package com.github.aakira.napier

import kotlinx.coroutines.launch

fun Sample.suspendHelloKt() {
    NativeScope().launch {
        suspendHello()
    }
}

fun Sample.handleErrorKt() {
    NativeScope().launch {
        handleError()
    }
}
