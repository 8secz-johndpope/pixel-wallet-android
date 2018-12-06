package com.piction.pixelwallet.lib.web3


class Web3Manager(var type: Web3Type) : Web3 {

    enum class Web3Type { WEB3J, WEB3JS }

    private lateinit var web3: Web3

    init {
        createWeb3()
    }

    private fun createWeb3() {
        web3 = when (type) {
            Web3Type.WEB3J -> Web3j()
            Web3Type.WEB3JS -> Web3js()
        }
    }

    fun updateWeb3Type(type: Web3Type) {
        this.type = type
        createWeb3()
    }

    override fun getVersion(result: (String) -> Unit) {
        web3.getVersion(result)
    }

}