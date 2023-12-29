package io.github.aakira.napier

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class NapierAndroidTest {

    @Test
    fun `Check createStackElementTag`() {
        val debugAntilog = DebugAntilog()

        assertEquals("Hoge", debugAntilog.createStackElementTag("io.github.aakira.napier.Hoge"))
        assertEquals(
            "Hoge2",
            debugAntilog.createStackElementTag("AA\$io.github.aakira.napier.Hoge2")
        )
        assertEquals(
            "Hoge3\$default",
            debugAntilog.createStackElementTag("io.github.aakira.napier.Hoge3\$default")
        )
        assertNotEquals(
            "default",
            debugAntilog.createStackElementTag("io.github.aakira.napier.Hoge3\$default")
        )
    }
}
