package com.piction.pixelwallet.lib.web3


class Web3Manager(type: Web3Type): IWeb3 {

    enum class Web3Type { WEB3J, WEB3JS}

    private var web3: IWeb3

    init {
        web3 = when (type) {
            Web3Type.WEB3J -> Web3j()
            Web3Type.WEB3JS -> Web3j() //Todo web3js
        }
    }

    override fun getVersion(): String {
        return web3.getVersion()
    }

}