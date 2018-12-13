package com.piction.pixelwallet.model

import com.piction.pixelwallet.lib.persistence.preferences.DynamicPreference

class User(private val dynamicPreference: DynamicPreference) {

    val userPinKey = "piction.pixelwallet.user_pin"

    init {
        //todo find WalletFile
        //set currentAccount
        //and add accountList
    }

    var currentAccount: Account? = null

    var accountList: MutableList<Account> = ArrayList()


    fun setPin(string: String) { dynamicPreference.put(userPinKey, "string") }

    fun equalsPin(string: String): Boolean = dynamicPreference.get(userPinKey, "") == string

    fun getPin(): String? = dynamicPreference.get(userPinKey, "")

    fun createAccount(password: String) {
        //todo
        //using WalletUtils


        //todo
        //set currentAccount
        //and add accountList
    }
}