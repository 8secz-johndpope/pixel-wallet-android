package com.piction.pixelwallet.lib.persistence.preferences

import android.content.SharedPreferences


open class DynamicPreference(
    private val sharedPreferences: SharedPreferences
) {
    fun contains(key: String) = sharedPreferences.contains(key)

    fun get(key: String, defaultValue: Boolean): Boolean = getInternal(key, defaultValue)

    fun get(key: String, defaultValue: Int): Int = getInternal(key, defaultValue)

    fun get(key: String, defaultValue: Long): Long = getInternal(key, defaultValue)

    fun get(key: String, defaultValue: String): String = getInternal(key, defaultValue)

    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
    protected open fun <T : Any> getInternal(key: String, defaultValue: T): T {
        val str = sharedPreferences.getString(key, "")
        if (str.isNullOrEmpty()) {
            return defaultValue
        }
        return when (defaultValue) {
            is Boolean -> str.toBoolean()
            is Int -> str.toInt()
            is Long -> str.toLong()
            is String -> str
            else -> throw IllegalArgumentException("defaultValue only could be one of these types: Boolean, Int, Long, String")
        } as T
    }

    fun put(key: String, value: Boolean) = putInternal(key, value)

    fun put(key: String, value: Int) = putInternal(key, value)

    fun put(key: String, value: Long) = putInternal(key, value)

    fun put(key: String, value: String) = putInternal(key, value)

    protected open fun putInternal(key: String, value: Any?) {
        try {
            sharedPreferences.edit().run {
                if (value == null) {
                    remove(key)
                } else {
                    putString(key, value.toString())
                }
                apply()
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

}