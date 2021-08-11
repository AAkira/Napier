package com.github.aakira.napier.atomic

internal expect class AtomicRef<T>(value: T) {
    var value: T
}