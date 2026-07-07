package io.github.aakira.napier

import org.junit.Test
import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.logging.Logger
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class NapierJvmTest {

    private val debugAntilog = DebugAntilog()

    private class RecordingHandler : Handler() {
        val records = mutableListOf<LogRecord>()

        override fun publish(record: LogRecord) {
            records.add(record)
        }

        override fun flush() = Unit

        override fun close() = Unit
    }

    private fun withRecordingHandler(block: (DebugAntilog, RecordingHandler) -> Unit) {
        val handler = RecordingHandler()
        val antilog = DebugAntilog("defaultTag", listOf(handler))
        try {
            block(antilog, handler)
        } finally {
            Logger.getLogger(DebugAntilog::class.java.name).removeHandler(handler)
        }
    }

    @Test
    fun `Check createStackElementTag`() {
        assertEquals("Hoge", debugAntilog.createStackElementTag("io.github.aakira.napier.Hoge"))
        assertEquals(
            "Hoge2",
            debugAntilog.createStackElementTag("AA\$io.github.aakira.napier.Hoge2")
        )
        assertNotEquals(
            "default",
            debugAntilog.createStackElementTag("io.github.aakira.napier.Hoge3\$default")
        )
        // anonymous class suffixes are stripped
        assertEquals("Hoge", debugAntilog.createStackElementTag("io.github.aakira.napier.Hoge\$1"))
        assertEquals(
            "Hoge",
            debugAntilog.createStackElementTag("io.github.aakira.napier.Hoge\$1\$2")
        )
    }

    @Test
    fun `Check buildLog`() {
        assertEquals(
            "[VERBOSE] defaultTag - message",
            debugAntilog.buildLog(LogLevel.VERBOSE, "defaultTag", "message")
        )
        assertEquals(
            "[DEBUG] defaultTag - message",
            debugAntilog.buildLog(LogLevel.DEBUG, "defaultTag", "message")
        )
        assertEquals(
            "[INFO] defaultTag - message",
            debugAntilog.buildLog(LogLevel.INFO, "defaultTag", "message")
        )
        assertEquals(
            "[WARN] defaultTag - message",
            debugAntilog.buildLog(LogLevel.WARNING, "defaultTag", "message")
        )
        assertEquals(
            "[ERROR] defaultTag - message",
            debugAntilog.buildLog(LogLevel.ERROR, "defaultTag", "message")
        )
        assertEquals(
            "[ASSERT] defaultTag - message",
            debugAntilog.buildLog(LogLevel.ASSERT, "defaultTag", "message")
        )
    }

    @Test
    fun `Check output log priority`() {
        withRecordingHandler { antilog, handler ->
            antilog.log(LogLevel.VERBOSE, "tag", null, "message")
            antilog.log(LogLevel.DEBUG, "tag", null, "message")
            antilog.log(LogLevel.INFO, "tag", null, "message")
            antilog.log(LogLevel.WARNING, "tag", null, "message")
            antilog.log(LogLevel.ERROR, "tag", null, "message")
            antilog.log(LogLevel.ASSERT, "tag", null, "message")

            assertEquals(6, handler.records.size)
            assertEquals(Level.FINEST, handler.records[0].level)
            assertEquals(Level.FINE, handler.records[1].level)
            assertEquals(Level.INFO, handler.records[2].level)
            assertEquals(Level.WARNING, handler.records[3].level)
            assertEquals(Level.SEVERE, handler.records[4].level)
            assertEquals(Level.SEVERE, handler.records[5].level)

            assertEquals("[VERBOSE] tag - message", handler.records[0].message)
            assertEquals("[DEBUG] tag - message", handler.records[1].message)
            assertEquals("[INFO] tag - message", handler.records[2].message)
            assertEquals("[WARN] tag - message", handler.records[3].message)
            assertEquals("[ERROR] tag - message", handler.records[4].message)
            assertEquals("[ASSERT] tag - message", handler.records[5].message)
        }
    }

    @Test
    fun `Check throwable message`() {
        withRecordingHandler { antilog, handler ->
            antilog.log(LogLevel.ERROR, "tag", Exception("error"), "message")

            assertEquals(1, handler.records.size)
            val logged = handler.records[0].message
            assertTrue(logged.startsWith("[ERROR] tag - message\n"))
            assertTrue(logged.contains("java.lang.Exception: error"))
        }
    }

    @Test
    fun `Check no output when message and throwable are null`() {
        withRecordingHandler { antilog, handler ->
            antilog.log(LogLevel.DEBUG, "tag", null, null)

            assertEquals(0, handler.records.size)
        }
    }
}
