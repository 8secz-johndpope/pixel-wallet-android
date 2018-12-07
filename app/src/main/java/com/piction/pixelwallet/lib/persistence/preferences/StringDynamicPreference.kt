package com.piction.pixelwallet.lib.persistence.preferences

import android.content.SharedPreferences
import androidx.core.content.edit

class StringDynamicPreference constructor(
    private val sharedPreferences: SharedPreferences,
    private val defaultValue: String?
) {

    operator fun get(key: String): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun isSet(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    operator fun set(key: String, value: String) {
        sharedPreferences.edit { putString(key, value) }
    }

    fun delete(key: String) {
        sharedPreferences.edit { remove(key) }
    }
}
