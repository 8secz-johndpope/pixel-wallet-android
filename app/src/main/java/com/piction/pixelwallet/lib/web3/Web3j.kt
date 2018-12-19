package com.piction.pixelwallet.lib.web3

import org.web3j.protocol.Web3j
import org.web3j.protocol.Web3jFactory
import org.web3j.protocol.http.HttpService
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class Web3j : Web3 {

    private val web3: Web3j = Web3jFactory.build(HttpService("https://private.piction.network:8545"))//ropsten.infura.io/v3/76fff91884f54fcd84c328956afc74d6"))

    override fun getVersion(result: (String) -> Unit) {
        web3.web3ClientVersion()
            .observable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result(it.web3ClientVersion) },{})
    }

}