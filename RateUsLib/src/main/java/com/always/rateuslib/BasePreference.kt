package com.always.rateuslib

import android.content.Context
import android.content.SharedPreferences

class BasePreference(private val cache: Cache) {
    companion object {
        const val CACHE_SHOW_RATE = "CACHE_SHOW_RATE"
        const val CACHE_IS_RATE = "CACHE_IS_RATE"
    }

    var countShowRate: Int
    get() = cache.appSharedPrefs.getInt(CACHE_SHOW_RATE, 0)
    set(value) {
        cache.putInt(CACHE_SHOW_RATE, value)
    }

    var isRate: Boolean
    get() = cache.appSharedPrefs.getBoolean(CACHE_IS_RATE, false)
    set(value) {
        cache.putBoolean(CACHE_IS_RATE, value)
    }

    fun plusCount() {
        countShowRate = countShowRate.plus(1)
    }

    class Cache(private val context: Context) {
        val appSharedPrefs: SharedPreferences by lazy {
            context.getSharedPreferences(
                Cache::class.java.simpleName,
                Context.MODE_PRIVATE
            )
        }

        private val prefsEditor: SharedPreferences.Editor by lazy {
            appSharedPrefs.edit()
        }

        fun putInt(key: String, value: Int) {
            prefsEditor.putInt(key, value).apply()
        }

        fun putBoolean(key: String, value: Boolean) {
            prefsEditor.putBoolean(key, value).apply()
        }

        fun builder(): BasePreference = BasePreference(this)
    }
}