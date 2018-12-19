package com.piction.pixelwallet.ui.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.piction.pixelwallet.lib.persistence.CurrentUser
import com.piction.pixelwallet.lib.persistence.repository.PixelWalletRepository
import com.piction.pixelwallet.lib.web3.Web3Manager
import com.piction.pixelwallet.model.Wallet
import com.piction.pixelwallet.model.WalletCard
import java.math.BigInteger
import javax.inject.Inject

class HomeActivityViewModel @Inject
constructor(
    private val currentUser: CurrentUser,
    private val web3Manager: Web3Manager,
    private val pixelWalletRepository: PixelWalletRepository
) : ViewModel() {

    private val walletListLiveData: LiveData<List<Wallet>> = pixelWalletRepository.getWalletList()
    private val walletCardListLiveData: LiveData<List<WalletCard>> = Transformations.switchMap(walletListLiveData) { it ->
        //todo pxl balance

        val lt = (it.map {
            WalletCard(it.address, it.name, it.file, BigInteger("0"))
        })
        MutableLiveData<List<WalletCard>>().apply { postValue(lt) }
    }

    val walletList: LiveData<List<WalletCard>> get() = walletCardListLiveData



    fun getBalance(wallets: List<Wallet>) {

    }

    fun createWallet() {
        pixelWalletRepository.createWallet(web3Manager.createWallet())
    }
}