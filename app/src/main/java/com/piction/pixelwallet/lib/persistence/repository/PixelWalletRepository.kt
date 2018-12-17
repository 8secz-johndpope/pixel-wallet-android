package com.piction.pixelwallet.lib.persistence.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.piction.pixelwallet.lib.keystore.FileBasedWalletKeyStore
import com.piction.pixelwallet.lib.persistence.db.dao.PixelWalletDAO
import com.piction.pixelwallet.lib.persistence.db.mapper.PixelWalletMapper
import com.piction.pixelwallet.model.Wallet
import org.jetbrains.anko.doAsync
import org.web3j.crypto.ECKeyPair


class PixelWalletRepository(
    val context: Context,
    private val pixelWalletDAO: PixelWalletDAO,
    private val fileBasedWalletKeyStore: FileBasedWalletKeyStore
) {
    fun createWallet(ecKeyPair: ECKeyPair) {
        doAsync {
            pixelWalletDAO.insertWallet(
                PixelWalletMapper.accountToEntity(fileBasedWalletKeyStore.putKey(ecKeyPair))
            )
        }
    }

    fun getWalletList(): LiveData<List<Wallet>> =
        Transformations.map(pixelWalletDAO.selectWalletList()) { list ->
            list.map { PixelWalletMapper.entityToAccount(it) }
        }

    fun deleteWallet(wallet: Wallet) {
        doAsync {
            if (fileBasedWalletKeyStore.deleteKey(wallet)) {
                pixelWalletDAO.deleteWallet(wallet.address.toString())
            }
        }
    }
}