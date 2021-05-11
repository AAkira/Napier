package io.github.aakira.napier.mppsample

import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay

class Sample {

    fun hello(): String {
        Napier.v("Hello napier")
        Napier.d("optional tag", tag = "your tag")

        return "Hello Napier"
    }

    suspend fun suspendHello(): String {
        Napier.i("Hello")

        delay(3000L)

        Napier.w("Napier!")

        return "Suspend Hello Napier"
    }

    fun handleError() {
        try {
            throw Exception("throw error")
        } catch (e: Exception) {
            Napier.e("Napier Error", e)
        }
    }
}
