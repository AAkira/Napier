package io.github.aakira.napier.atomic

import kotlin.concurrent.AtomicReference

internal actual class AtomicRef<T> actual constructor(value: T) {
    private val atomicRef = AtomicReference(value)
    actual var value: T
        get() = atomicRef.value
        set(value) {
            atomicRef.value = value
        }
}
