package io.github.aakira.napier

internal class CopyOnWriteList<T>(value: List<T>) : AbstractList<T>() {
    constructor() : this(listOf())

    private var list = value

    fun add(element: T, index: Int = count()) =
        modify(+1) {
            add(index, element)
        }

    fun remove(t: T) =
        modify(-1) {
            remove(t)
        }

    fun clear() =
        modify(-size) {
            clear()
        }

    fun removeAt(index: Int): T =
        modify(-1) {
            removeAt(index)
        }

    fun set(index: Int, element: T): T =
        modify(0) {
            set(index, element)
        }

    fun dropAll(): List<T> {
        val result = list
        list = listOf()
        return result
    }

    override val size: Int get() = list.size
    override fun isEmpty(): Boolean = list.isEmpty()
    override fun contains(element: T): Boolean = list.contains(element)
    override fun get(index: Int): T = list[index]
    override fun indexOf(element: T): Int = list.indexOf(element)
    override fun lastIndexOf(element: T): Int = list.lastIndexOf(element)
    override fun iterator(): Iterator<T> = list.iterator()

    private fun <R> modify(capacityDiff: Int, block: ArrayList<T>.() -> R): R {
        val newValue = ArrayList<T>(size + capacityDiff)
        newValue.addAll(this)
        val result = block(newValue)
        list = newValue
        return result
    }
}
