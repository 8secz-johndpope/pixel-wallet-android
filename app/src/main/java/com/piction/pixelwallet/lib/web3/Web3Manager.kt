package com.piction.pixelwallet.lib.web3

import com.piction.pixelwallet.lib.keystore.WalletKeyStore
import com.piction.pixelwallet.lib.persistence.preferences.SecureDynamicPreference
import com.piction.pixelwallet.model.Address

import org.web3j.crypto.Keys
import java.util.*


class Web3Manager(
    private val walletKeyStore: WalletKeyStore,
    private val secureDynamicPreference: SecureDynamicPreference
) : Web3 {

    val pwdKey = "piction_pixelwallet"

    init {
        if (secureDynamicPreference.get(pwdKey, "").isEmpty()) {
            secureDynamicPreference.put(pwdKey, UUID.randomUUID().toString())
        }
    }
    private var web3: Web3 = Web3j()

    override fun getVersion(result: (String) -> Unit) {
        web3.getVersion(result)
    }

    fun createWallet(): Address? =
        walletKeyStore.importKey(Keys.createEcKeyPair(), secureDynamicPreference.get(pwdKey, ""))
}