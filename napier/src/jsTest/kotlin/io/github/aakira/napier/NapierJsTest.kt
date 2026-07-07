package io.github.aakira.napier

import kotlin.test.Test
import kotlin.test.assertEquals

class NapierJsTest {

    private fun withCapturedConsole(block: () -> Unit): List<String> {
        val captured = mutableListOf<String>()
        val console = js("console")
        val originalLog = console.log
        val originalInfo = console.info
        val originalWarn = console.warn
        val originalError = console.error

        console.log = { message: String -> captured.add("log:$message") }
        console.info = { message: String -> captured.add("info:$message") }
        console.warn = { message: String -> captured.add("warn:$message") }
        console.error = { message: String -> captured.add("error:$message") }

        try {
            block()
        } finally {
            console.log = originalLog
            console.info = originalInfo
            console.warn = originalWarn
            console.error = originalError
        }
        return captured
    }

    @Test
    fun `Check output log priority`() {
        val logs = withCapturedConsole {
            val antilog = DebugAntilog("app")
            antilog.log(LogLevel.VERBOSE, "tag", null, "hello")
            antilog.log(LogLevel.DEBUG, "tag", null, "hello")
            antilog.log(LogLevel.INFO, "tag", null, "hello")
            antilog.log(LogLevel.WARNING, "tag", null, "hello")
            antilog.log(LogLevel.ERROR, "tag", null, "hello")
            antilog.log(LogLevel.ASSERT, "tag", null, "hello")
        }

        assertEquals(
            listOf(
                "log:VERBOSE tag : hello",
                "log:DEBUG tag : hello",
                "info:INFO tag : hello",
                "warn:WARNING tag : hello",
                "error:ERROR tag : hello",
                "error:ASSERT tag : hello",
            ),
            logs
        )
    }

    @Test
    fun `Check default tag`() {
        val logs = withCapturedConsole {
            DebugAntilog("app").log(LogLevel.DEBUG, null, null, "hello")
        }

        assertEquals(listOf("log:DEBUG app : hello"), logs)
    }

    @Test
    fun `Check throwable message`() {
        val logs = withCapturedConsole {
            val antilog = DebugAntilog("app")
            antilog.log(LogLevel.ERROR, "tag", Exception("error"), "hello")
            antilog.log(LogLevel.ERROR, "tag", Exception("error"), null)
        }

        assertEquals(
            listOf(
                "error:ERROR tag : hello\nerror",
                "error:ERROR tag : error",
            ),
            logs
        )
    }

    @Test
    fun `Check no output when message and throwable are null`() {
        val logs = withCapturedConsole {
            DebugAntilog("app").log(LogLevel.DEBUG, "tag", null, null)
        }

        assertEquals(emptyList(), logs)
    }
}
