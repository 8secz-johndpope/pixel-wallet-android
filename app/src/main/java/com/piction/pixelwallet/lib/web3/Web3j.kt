package com.piction.pixelwallet.lib.web3

import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.Web3jFactory
import org.web3j.protocol.http.HttpService


class Web3j : Web3 {

    private val web3: Web3j = Web3jFactory.build(HttpService("https://ropsten.infura.io/v3/76fff91884f54fcd84c328956afc74d6"))

    override fun getVersion(): String {
        return web3.web3ClientVersion().sendAsync().get().web3ClientVersion
    }

    /*fun getAccount() {
        WalletUtils.generateWalletFile()
    }*/

}