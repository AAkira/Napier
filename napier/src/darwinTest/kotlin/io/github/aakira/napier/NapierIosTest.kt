package io.github.aakira.napier

import kotlin.test.Test
import kotlin.test.assertEquals

class NapierIosTest {

    @Test
    fun `Check createStackElementTag`() {
        val debugAntilog = DebugAntilog()

        val sampleHello = debugAntilog.createStackElementTag(
            "8   Napier                              0x0000000100f0eb3d kfun:io.github.aakira.napier.mppsample.Sample#hello(){}kotlin.String + 205"
        )
        assertEquals("Sample.hello", sampleHello)

        val handleError = debugAntilog.createStackElementTag(
            "8   Napier                              0x0000000100f0f57c kfun:io.github.aakira.napier.mppsample.Sample#handleError(){} + 412"
        )
        assertEquals("Sample.handleError", handleError)

        val coroutine = debugAntilog.createStackElementTag(
            "8   Napier                              0x0000000100f0f0e2 kfun:io.github.aakira.napier.mppsample.Sample.\$suspendHelloCOROUTINE\$2#invokeSuspend(kotlin.Result<kotlin.Any?>){}kotlin.Any? + 930"
        )
        assertEquals("Sample.suspendHello[async]", coroutine)
    }

    @Test
    fun `Check createStackElementTag without coroutines suffix`() {
        val debugAntilog = DebugAntilog(
            coroutinesSuffix = false,
        )

        val sampleHello = debugAntilog.createStackElementTag(
            "8   Napier                              0x0000000100f0eb3d kfun:io.github.aakira.napier.mppsample.Sample#hello(){}kotlin.String + 205"
        )
        assertEquals("Sample.hello", sampleHello)

        val handleError = debugAntilog.createStackElementTag(
            "8   Napier                              0x0000000100f0f57c kfun:io.github.aakira.napier.mppsample.Sample#handleError(){} + 412"
        )
        assertEquals("Sample.handleError", handleError)

        val coroutine = debugAntilog.createStackElementTag(
            "8   Napier                              0x0000000100f0f0e2 kfun:io.github.aakira.napier.mppsample.Sample.\$suspendHelloCOROUTINE\$2#invokeSuspend(kotlin.Result<kotlin.Any?>){}kotlin.Any? + 930"
        )
        assertEquals("Sample.suspendHello", coroutine)
    }
}
