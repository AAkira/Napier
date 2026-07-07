package io.github.aakira.napier

import io.github.aakira.napier.atomic.AtomicMutableList
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NapierTest {

    private data class NapierTestCase(
        val name: String,
        val run: () -> Unit,
        val expected: Expected
    )

    private data class Expected(
        val priority: LogLevel,
        val tag: String?,
        val throwable: Throwable?,
        val message: String?
    )

    private data class CustomThrowable(override val message: String) : Throwable(message)

    private fun recordOutput(output: AtomicMutableList<Expected>): Antilog = object : Antilog() {
        override fun performLog(
            priority: LogLevel,
            tag: String?,
            throwable: Throwable?,
            message: String?,
        ) {
            output.add(Expected(priority, tag, throwable, message))
        }
    }

    @AfterTest
    fun tearDown() {
        Napier.takeLogarithm()
    }

    @Test
    fun `Check output log`() {
        val output = AtomicMutableList<Expected>()
        Napier.base(recordOutput(output))

        val testCase = listOf(
            NapierTestCase(
                "verbose",
                { Napier.v("hello") },
                Expected(
                    LogLevel.VERBOSE,
                    null,
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "debug",
                { Napier.d("hello") },
                Expected(
                    LogLevel.DEBUG,
                    null,
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "info",
                { Napier.i("hello") },
                Expected(
                    LogLevel.INFO,
                    null,
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "warning",
                { Napier.w("hello") },
                Expected(
                    LogLevel.WARNING,
                    null,
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "error",
                { Napier.e("hello") },
                Expected(
                    LogLevel.ERROR,
                    null,
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "assert",
                { Napier.wtf("hello") },
                Expected(
                    LogLevel.ASSERT,
                    null,
                    null,
                    "hello"
                )
            ),
            // tag check
            NapierTestCase(
                "tag verbose",
                { Napier.v("hello", null, "tag") },
                Expected(
                    LogLevel.VERBOSE,
                    "tag",
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "tag debug",
                { Napier.d("hello", null, "tag") },
                Expected(
                    LogLevel.DEBUG,
                    "tag",
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "tag info",
                { Napier.i("hello", null, "tag") },
                Expected(
                    LogLevel.INFO,
                    "tag",
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "tag warning",
                { Napier.w("hello", null, "tag") },
                Expected(
                    LogLevel.WARNING,
                    "tag",
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "tag error",
                { Napier.e("hello", null, "tag") },
                Expected(
                    LogLevel.ERROR,
                    "tag",
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "tag assert",
                { Napier.wtf("hello", null, "tag") },
                Expected(
                    LogLevel.ASSERT,
                    "tag",
                    null,
                    "hello"
                )
            ),
            // throwable
            NapierTestCase(
                "throwable verbose",
                { Napier.v("hello", CustomThrowable("error"), "tag") },
                Expected(
                    LogLevel.VERBOSE,
                    "tag",
                    CustomThrowable("error"),
                    "hello"
                )
            ),
            NapierTestCase(
                "throwable debug",
                { Napier.d("hello", CustomThrowable("error"), "tag") },
                Expected(
                    LogLevel.DEBUG,
                    "tag",
                    CustomThrowable("error"),
                    "hello"
                )
            ),
            NapierTestCase(
                "throwable info",
                { Napier.i("hello", CustomThrowable("error"), "tag") },
                Expected(
                    LogLevel.INFO,
                    "tag",
                    CustomThrowable("error"),
                    "hello"
                )
            ),
            NapierTestCase(
                "throwable warning",
                { Napier.w("hello", CustomThrowable("error"), "tag") },
                Expected(
                    LogLevel.WARNING,
                    "tag",
                    CustomThrowable("error"),
                    "hello"
                )
            ),
            NapierTestCase(
                "throwable error",
                { Napier.e("hello", CustomThrowable("error"), "tag") },
                Expected(
                    LogLevel.ERROR,
                    "tag",
                    CustomThrowable("error"),
                    "hello"
                )
            ),
            NapierTestCase(
                "throwable assert",
                { Napier.wtf("hello", CustomThrowable("error"), "tag") },
                Expected(
                    LogLevel.ASSERT,
                    "tag",
                    CustomThrowable("error"),
                    "hello"
                )
            )
        )

        // exercise
        testCase.forEach { it.run.invoke() }

        // verify
        assertEquals(testCase.size, output.size)

        testCase.forEachIndexed { index, case ->
            assertEquals(output[index], case.expected)
        }
    }

    @Test
    fun `Check lambda log output`() {
        val output = AtomicMutableList<Expected>()
        Napier.base(recordOutput(output))

        val throwable = CustomThrowable("error")

        Napier.v { "verbose" }
        Napier.d { "debug" }
        Napier.i { "info" }
        Napier.w { "warning" }
        Napier.e { "error" }
        Napier.wtf { "assert" }
        Napier.d(throwable, "tag") { "debug" }

        assertEquals(7, output.size)
        assertEquals(Expected(LogLevel.VERBOSE, null, null, "verbose"), output[0])
        assertEquals(Expected(LogLevel.DEBUG, null, null, "debug"), output[1])
        assertEquals(Expected(LogLevel.INFO, null, null, "info"), output[2])
        assertEquals(Expected(LogLevel.WARNING, null, null, "warning"), output[3])
        assertEquals(Expected(LogLevel.ERROR, null, null, "error"), output[4])
        assertEquals(Expected(LogLevel.ASSERT, null, null, "assert"), output[5])
        assertEquals(Expected(LogLevel.DEBUG, "tag", throwable, "debug"), output[6])
    }

    @Test
    fun `Check top-level log output`() {
        val output = AtomicMutableList<Expected>()
        Napier.base(recordOutput(output))

        log { "hello" }
        log(LogLevel.INFO, tag = "tag") { "hello" }

        assertEquals(2, output.size)
        assertEquals(Expected(LogLevel.DEBUG, null, null, "hello"), output[0])
        assertEquals(Expected(LogLevel.INFO, "tag", null, "hello"), output[1])
    }

    @Test
    fun `Check multiple antilogs receive log`() {
        val output1 = AtomicMutableList<Expected>()
        val output2 = AtomicMutableList<Expected>()
        Napier.base(recordOutput(output1))
        Napier.base(recordOutput(output2))

        Napier.d("hello")

        assertEquals(1, output1.size)
        assertEquals(1, output2.size)
    }

    @Test
    fun `Check takeLogarithm removes antilog`() {
        val output = AtomicMutableList<Expected>()
        val antilog = recordOutput(output)
        Napier.base(antilog)

        Napier.d("first")
        Napier.takeLogarithm(antilog)
        Napier.d("second")

        assertEquals(1, output.size)
        assertEquals(Expected(LogLevel.DEBUG, null, null, "first"), output[0])
    }

    @Test
    fun `Check takeLogarithm removes all antilogs`() {
        val output = AtomicMutableList<Expected>()
        Napier.base(recordOutput(output))
        Napier.base(recordOutput(output))

        Napier.takeLogarithm()
        Napier.d("hello")

        assertEquals(0, output.size)
    }

    @Test
    fun `Check isEnable filtering`() {
        val output = AtomicMutableList<Expected>()
        Napier.base(object : Antilog() {
            override fun isEnable(priority: LogLevel, tag: String?) =
                priority >= LogLevel.WARNING

            override fun performLog(
                priority: LogLevel,
                tag: String?,
                throwable: Throwable?,
                message: String?,
            ) {
                output.add(Expected(priority, tag, throwable, message))
            }
        })

        assertFalse(Napier.isEnable(LogLevel.VERBOSE, null))
        assertFalse(Napier.isEnable(LogLevel.DEBUG, null))
        assertTrue(Napier.isEnable(LogLevel.WARNING, null))
        assertTrue(Napier.isEnable(LogLevel.ERROR, null))

        Napier.v("verbose")
        Napier.d("debug")
        Napier.w("warning")
        Napier.e("error")

        assertEquals(2, output.size)
        assertEquals(Expected(LogLevel.WARNING, null, null, "warning"), output[0])
        assertEquals(Expected(LogLevel.ERROR, null, null, "error"), output[1])
    }
}
