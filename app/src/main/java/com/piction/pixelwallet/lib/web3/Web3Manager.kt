package com.piction.pixelwallet.lib.web3

import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Keys


class Web3Manager : Web3 {

    private var web3: Web3 = Web3j()

    override fun getVersion(result: (String) -> Unit) {
        web3.getVersion(result)
    }

    fun createWallet(): ECKeyPair =
        Keys.createEcKeyPair()
}