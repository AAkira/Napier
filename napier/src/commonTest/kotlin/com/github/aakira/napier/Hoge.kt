package com.github.aakira.napier

class Hoge {

    fun hello(): String {
        Napier.v("Hoge")

        return "hello"
    }

    suspend fun suspendHello(): String {
        Napier.v("World!")
        Napier.e("World!")
        Napier.i("World!")

//        testRunBlocking {
//            delay(500L)
//        }

        Napier.v("Hello,")

        return "aa"
    }
}
