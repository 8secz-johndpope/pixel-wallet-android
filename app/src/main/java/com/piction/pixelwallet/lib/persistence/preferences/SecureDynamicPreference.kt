package com.piction.pixelwallet.lib.persistence.preferences

import android.content.SharedPreferences
import com.piction.pixelwallet.lib.secure.CipherHelper


class SecureDynamicPreference(
    private val sharedPreferences: SharedPreferences,
    private val cipherHelper: CipherHelper
): DynamicPreference(sharedPreferences) {

    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
    override fun <T : Any> getInternal(key: String, defaultValue: T): T {
        val str = sharedPreferences.getString(key, "")
        if (str.isNullOrEmpty()) {
            return defaultValue
        }

        val value = cipherHelper.decrypt(str)

        return when (defaultValue) {
            is Boolean -> value.toBoolean()
            is Int -> value.toInt()
            is Long -> value.toLong()
            is String -> value
            else -> throw IllegalArgumentException("defaultValue only could be one of these types: Boolean, Int, Long, String")
        } as T
    }

    override fun putInternal(key: String, value: Any?) {
        try {
            sharedPreferences.edit().run {
                if (value == null) {
                    remove(key)
                } else {
                    putString(key, cipherHelper.encrypt(value.toString()))
                }
                apply()
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

}