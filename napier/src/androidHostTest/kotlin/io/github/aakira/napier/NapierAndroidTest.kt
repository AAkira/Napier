package io.github.aakira.napier

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class NapierAndroidTest {

    private val debugAntilog = DebugAntilog()

    @Test
    fun `Check createStackElementTag`() {
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

    @Test
    fun `Check createStackElementTag removes anonymous class suffix`() {
        assertEquals("Hoge", debugAntilog.createStackElementTag("io.github.aakira.napier.Hoge\$1"))
        assertEquals(
            "Hoge",
            debugAntilog.createStackElementTag("io.github.aakira.napier.Hoge\$1\$2")
        )
    }

    @Test
    fun `Check createStackElementTag truncates long tag`() {
        // Build.VERSION.SDK_INT is 0 in host tests, so the tag is truncated to 23 characters
        assertEquals(
            "VeryVeryVeryLongClassNa",
            debugAntilog.createStackElementTag(
                "io.github.aakira.napier.VeryVeryVeryLongClassNameOverLimit"
            )
        )
    }
}
