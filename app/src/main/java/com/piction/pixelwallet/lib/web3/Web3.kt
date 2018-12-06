package com.piction.pixelwallet.lib.web3


interface Web3 {
    fun getVersion(result: (String) -> Unit)
}