package com.piction.pixelwallet.ui.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.piction.pixelwallet.lib.persistence.CurrentUser
import com.piction.pixelwallet.lib.persistence.repository.PixelWalletRepository
import com.piction.pixelwallet.lib.web3.Web3Manager
import com.piction.pixelwallet.model.Wallet
import javax.inject.Inject

class HomeActivityViewModel @Inject
constructor(
    private val currentUser: CurrentUser,
    private val web3Manager: Web3Manager,
    private val pixelWalletRepository: PixelWalletRepository
) : ViewModel() {

    private val versionLiveData: MutableLiveData<String> = MutableLiveData()
    val version: LiveData<String> get() = versionLiveData

    private val startActivityLiveData: MutableLiveData<String> = MutableLiveData()
    val startActivity: LiveData<String> get() = startActivityLiveData

    private val walletListLiveData: LiveData<List<Wallet>> = pixelWalletRepository.getWalletList()
    val walletList: LiveData<List<Wallet>> get() = walletListLiveData


    fun startActivity(msg: String) {
        startActivityLiveData.postValue(msg)
    }

    fun createWallet() {
        pixelWalletRepository.createWallet(web3Manager.createWallet())
    }
}