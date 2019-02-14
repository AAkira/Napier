package com.github.aakira.napier

import kotlinx.coroutines.launch

fun Hoge.hoge() {
    NativeScope().launch {
        suspendHello()
    }
}
