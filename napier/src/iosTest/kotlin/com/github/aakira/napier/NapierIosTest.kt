package com.github.aakira.napier

import kotlin.test.Test
import kotlin.test.assertEquals

class NapierIosTest {

    @Test
    fun `Check createStackElementTag`() {
        val debugAntilog = DebugAntilog()

        val sampleHello = debugAntilog.createStackElementTag(
            "::: 8   Common   0x000000010db38cb3 kfun:com.github.aakira.napier.mppsample.Sample.hello()kotlin.String + 211"
        )
        assertEquals("Sample.hello", sampleHello)

        val handleError = debugAntilog.createStackElementTag(
            "::: 8   Common     0x000000010db396c0 kfun:com.github.aakira.napier.mppsample.Sample.handleError() + 432"
        )
        assertEquals("Sample.handleError", handleError)

        val coroutine = debugAntilog.createStackElementTag(
            "::: 8   Common     0x000000010db39132 kfun:com.github.aakira.napier.mppsample.Sample.\$suspendHelloCOROUTINE\$0.invokeSuspend(kotlin.Result<kotlin.Any?>)kotlin.Any? + 626"
        )
        assertEquals("Sample.suspendHello", coroutine)
    }
}
