package com.always.rateuslib

import android.content.SharedPreferences

class BasePreference(private val cache: Cache) {
    companion object {
        const val CACHE_SHOW_RATE = "CACHE_SHOW_RATE"
        const val CACHE_IS_RATE = "CACHE_IS_RATE"
    }

    var countShowRate: Int
    get() = cache.sharedPreferences.getInt(CACHE_SHOW_RATE, 0)
    set(value) {
        cache.prefsEditor.putInt(CACHE_SHOW_RATE, value)
    }

    var isRate: Boolean
    get() = cache.sharedPreferences.getBoolean(CACHE_IS_RATE, false)
    set(value) {
        cache.prefsEditor.putBoolean(CACHE_IS_RATE, value)
    }

    fun plusCount() {
        countShowRate = ++countShowRate
    }

    class Cache(val sharedPreferences: SharedPreferences) {
        val prefsEditor: SharedPreferences.Editor by lazy {
            sharedPreferences.edit()
        }

        fun builder(): BasePreference = BasePreference(this)
    }
}