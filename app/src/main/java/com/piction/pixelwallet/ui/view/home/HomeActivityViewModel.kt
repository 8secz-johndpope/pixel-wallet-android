package com.piction.pixelwallet.ui.view.home

import androidx.lifecycle.ViewModel
import com.piction.pixelwallet.lib.web3.Web3Manager
import javax.inject.Inject

class HomeActivityViewModel @Inject
constructor(
    private val web3Manager: Web3Manager

) : ViewModel() {

    fun getWeb3Version(): String = web3Manager.getVersion()
}