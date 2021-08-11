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
        val priority: Napier.Level,
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
                priority: Napier.Level,
                tag: String?,
                throwable: Throwable?,
                message: String?
            ) {
                output.add(Expected(priority, tag, throwable, message))
            }
        })

        val testCase = listOf(
            NapierTestCase(
                "verbose",
                { Napier.v("hello") },
                Expected(
                    Napier.Level.VERBOSE,
                    null,
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "debug",
                { Napier.d("hello") },
                Expected(
                    Napier.Level.DEBUG,
                    null,
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "info",
                { Napier.i("hello") },
                Expected(
                    Napier.Level.INFO,
                    null,
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "warning",
                { Napier.w("hello") },
                Expected(
                    Napier.Level.WARNING,
                    null,
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "error",
                { Napier.e("hello") },
                Expected(
                    Napier.Level.ERROR,
                    null,
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "assert",
                { Napier.wtf("hello") },
                Expected(
                    Napier.Level.ASSERT,
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
                    Napier.Level.VERBOSE,
                    "tag",
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "tag debug",
                { Napier.d("hello", null, "tag") },
                Expected(
                    Napier.Level.DEBUG,
                    "tag",
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "tag info",
                { Napier.i("hello", null, "tag") },
                Expected(
                    Napier.Level.INFO,
                    "tag",
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "tag warning",
                { Napier.w("hello", null, "tag") },
                Expected(
                    Napier.Level.WARNING,
                    "tag",
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "tag error",
                { Napier.e("hello", null, "tag") },
                Expected(
                    Napier.Level.ERROR,
                    "tag",
                    null,
                    "hello"
                )
            ),
            NapierTestCase(
                "tag assert",
                { Napier.wtf("hello", null, "tag") },
                Expected(
                    Napier.Level.ASSERT,
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
                    Napier.Level.VERBOSE,
                    "tag",
                    CustomThrowable("error"),
                    "hello"
                )
            ),
            NapierTestCase(
                "throwable debug",
                { Napier.d("hello", CustomThrowable("error"), "tag") },
                Expected(
                    Napier.Level.DEBUG,
                    "tag",
                    CustomThrowable("error"),
                    "hello"
                )
            ),
            NapierTestCase(
                "throwable info",
                { Napier.i("hello", CustomThrowable("error"), "tag") },
                Expected(
                    Napier.Level.INFO,
                    "tag",
                    CustomThrowable("error"),
                    "hello"
                )
            ),
            NapierTestCase(
                "throwable warning",
                { Napier.w("hello", CustomThrowable("error"), "tag") },
                Expected(
                    Napier.Level.WARNING,
                    "tag",
                    CustomThrowable("error"),
                    "hello"
                )
            ),
            NapierTestCase(
                "throwable error",
                { Napier.e("hello", CustomThrowable("error"), "tag") },
                Expected(
                    Napier.Level.ERROR,
                    "tag",
                    CustomThrowable("error"),
                    "hello"
                )
            ),
            NapierTestCase(
                "throwable assert",
                { Napier.wtf("hello", CustomThrowable("error"), "tag") },
                Expected(
                    Napier.Level.ASSERT,
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
