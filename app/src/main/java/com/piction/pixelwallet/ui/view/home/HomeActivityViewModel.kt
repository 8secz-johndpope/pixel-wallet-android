package com.piction.pixelwallet.ui.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.piction.pixelwallet.lib.web3.Web3Manager
import javax.inject.Inject

class HomeActivityViewModel @Inject
constructor(
    private val web3Manager: Web3Manager

) : ViewModel() {

    private val versionLiveData: MutableLiveData<String> = MutableLiveData()
    val version: LiveData<String> get() = versionLiveData

    fun getWeb3Version(): Unit = web3Manager.getVersion { versionLiveData.postValue(it) }
}