package com.piction.pixelwallet.model

import com.piction.pixelwallet.lib.persistence.preferences.StringDynamicPreference

class User(private val stringDynamicPreference: StringDynamicPreference) {

    val userPinKey = "piction.pixelwallet.user_pin"

    init {
        //todo find WalletFile
        //set currentAccount
        //and add accountList
    }

    var currentAccount: Account? = null

    var accountList: MutableList<Account> = ArrayList()


    fun setPin(string: String) { stringDynamicPreference[userPinKey] = string }

    fun equalsPin(string: String): Boolean = stringDynamicPreference[userPinKey]?.let { it == string } ?: let { false }

    fun getPin(): String? = stringDynamicPreference[userPinKey]

    fun createAccount(password: String) {
        //todo
        //using WalletUtils


        //todo
        //set currentAccount
        //and add accountList
    }
}