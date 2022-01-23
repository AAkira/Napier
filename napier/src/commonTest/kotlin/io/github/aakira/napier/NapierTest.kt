package io.github.aakira.napier

import io.github.aakira.napier.atomic.AtomicMutableList
import kotlin.test.Test
import kotlin.test.assertEquals

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

    @Test
    fun `Check output log`() {
        val output = AtomicMutableList<Expected>()
        Napier.base(object : Antilog() {
            override fun performLog(
                priority: LogLevel,
                tag: String?,
                throwable: Throwable?,
                message: String?,
            ) {
                output.add(Expected(priority, tag, throwable, message))
            }
        })

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
                { Napier.v("hello", "tag", null) },
                Expected(
                    LogLevel.VERBOSE,
                    "tag",
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "tag debug",
                { Napier.d("hello", "tag", null) },
                Expected(
                    LogLevel.DEBUG,
                    "tag",
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "tag info",
                { Napier.i("hello", "tag", null) },
                Expected(
                    LogLevel.INFO,
                    "tag",
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "tag warning",
                { Napier.w("hello", "tag", null) },
                Expected(
                    LogLevel.WARNING,
                    "tag",
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "tag error",
                { Napier.e("hello", "tag", null) },
                Expected(
                    LogLevel.ERROR,
                    "tag",
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "tag assert",
                { Napier.wtf("hello", "tag", null) },
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
                { Napier.v("hello", "tag", CustomThrowable("error")) },
                Expected(
                    LogLevel.VERBOSE,
                    "tag",
                    CustomThrowable("error"),
                    "hello"
                )
            ),
            NapierTestCase(
                "throwable debug",
                { Napier.d("hello", "tag", CustomThrowable("error")) },
                Expected(
                    LogLevel.DEBUG,
                    "tag",
                    CustomThrowable("error"),
                    "hello"
                )
            ),
            NapierTestCase(
                "throwable info",
                { Napier.i("hello", "tag", CustomThrowable("error")) },
                Expected(
                    LogLevel.INFO,
                    "tag",
                    CustomThrowable("error"),
                    "hello"
                )
            ),
            NapierTestCase(
                "throwable warning",
                { Napier.w("hello", "tag", CustomThrowable("error")) },
                Expected(
                    LogLevel.WARNING,
                    "tag",
                    CustomThrowable("error"),
                    "hello"
                )
            ),
            NapierTestCase(
                "throwable error",
                { Napier.e("hello", "tag", CustomThrowable("error")) },
                Expected(
                    LogLevel.ERROR,
                    "tag",
                    CustomThrowable("error"),
                    "hello"
                )
            ),
            NapierTestCase(
                "throwable assert",
                { Napier.wtf("hello", "tag", CustomThrowable("error")) },
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
}
