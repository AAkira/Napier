package io.github.aakira.napier

import kotlin.test.Test
import kotlin.test.assertEquals

private fun startConsoleCapture(): Unit = js(
    """{
        globalThis.napierTestCaptured = [];
        globalThis.napierTestOriginalConsole = {
            log: console.log,
            info: console.info,
            warn: console.warn,
            error: console.error
        };
        console.log = function (message) { globalThis.napierTestCaptured.push('log:' + message); };
        console.info = function (message) { globalThis.napierTestCaptured.push('info:' + message); };
        console.warn = function (message) { globalThis.napierTestCaptured.push('warn:' + message); };
        console.error = function (message) { globalThis.napierTestCaptured.push('error:' + message); };
    }"""
)

private fun stopConsoleCapture(): Unit = js(
    """{
        console.log = globalThis.napierTestOriginalConsole.log;
        console.info = globalThis.napierTestOriginalConsole.info;
        console.warn = globalThis.napierTestOriginalConsole.warn;
        console.error = globalThis.napierTestOriginalConsole.error;
    }"""
)

private fun capturedLogSize(): Int = js("globalThis.napierTestCaptured.length")

private fun capturedLogAt(index: Int): String = js("globalThis.napierTestCaptured[index]")

class NapierJsTest {

    private fun withCapturedConsole(block: () -> Unit): List<String> {
        startConsoleCapture()
        try {
            block()
        } finally {
            stopConsoleCapture()
        }
        return List(capturedLogSize()) { capturedLogAt(it) }
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
