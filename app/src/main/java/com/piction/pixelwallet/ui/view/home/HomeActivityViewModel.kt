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

    private val startActivityLiveData: MutableLiveData<String> = MutableLiveData()
    val startActivity: LiveData<String> get() = startActivityLiveData

    fun getWeb3Version(): Unit = web3Manager.getVersion { versionLiveData.postValue(it) }

    fun startActivity(msg: String) {
        startActivityLiveData.postValue(msg)
    }
}