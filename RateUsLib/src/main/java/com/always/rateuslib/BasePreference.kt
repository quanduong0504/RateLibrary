package com.always.rateuslib

import android.content.Context
import android.content.SharedPreferences

class BasePreference(private val cache: Cache) {
    companion object {
        const val CACHE_SHOW_RATE = "CACHE_SHOW_RATE"
    }

    var countShowRate: Int
    get() = cache.appSharedPrefs.getInt(CACHE_SHOW_RATE, 0)
    set(value) {
        cache.prefsEditor.putInt(CACHE_SHOW_RATE, value)
    }

    fun plusCount() {
        countShowRate = ++countShowRate
    }

    class Cache(private val context: Context) {
        val appSharedPrefs: SharedPreferences by lazy {
            context.getSharedPreferences(
                Cache::class.java.simpleName,
                Context.MODE_PRIVATE
            )
        }

        val prefsEditor: SharedPreferences.Editor by lazy {
            appSharedPrefs.edit()
        }

        fun builder(): BasePreference = BasePreference(this)
    }
}