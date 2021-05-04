package com.always.rateuslib

object ShowRateCountManager {
    private val numbers = mutableListOf(1, 2, 3, 4, 6, 8, 10)

    fun setup(numbers: MutableList<Int>) {
        this.numbers.clear()
        this.numbers.addAll(numbers)
    }

    fun check(count: Int) = numbers.getOrNull(count) != null

    fun getNumbers() = numbers
}