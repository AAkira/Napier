package com.github.aakira.napier

import org.junit.Before
import org.junit.Test
import java.lang.reflect.Method
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


class NapierTest {

    val debugAntilog = DebugAntilog()

    @Before
    fun init() {
        Napier.base(debugAntilog)
    }

    @Test
    fun createStackElementTag() {
        val method: Method = debugAntilog.javaClass.getDeclaredMethod("createStackElementTag", String::class.java)
        method.isAccessible = true

        assertEquals("Hoge", method.invoke(debugAntilog, "com.github.aakira.napier.Hoge") as String)
        assertEquals("Hoge2", method.invoke(debugAntilog, "AA\$com.github.aakira.napier.Hoge2") as String)
        assertNotEquals("default", method.invoke(debugAntilog, "com.github.aakira.napier.Hoge3\$default") as String)
    }

    @Test
    fun buildLog() {
        val method: Method = debugAntilog.javaClass.getDeclaredMethod("buildLog",
            Napier.Level::class.java, String::class.java, String::class.java)
        method.isAccessible = true

        assertEquals("[VERBOSE] defaultTag - message",
            method.invoke(debugAntilog, Napier.Level.VERBOSE, "defaultTag", "message") as String)
    }
}
