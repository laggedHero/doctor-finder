package net.laggedhero.doctorfinder.consumable

data class Consumable<T>(private var value: T?) {
    fun get(): T? {
        val toConsume = value
        value = null
        return toConsume
    }
}