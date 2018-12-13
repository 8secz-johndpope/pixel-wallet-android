package com.piction.pixelwallet.lib.web3

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.piction.pixelwallet.lib.persistence.preferences.DynamicPreference
import com.piction.pixelwallet.lib.secure.CipherHelper
import com.piction.pixelwallet.lib.secure.PictionKeyStore
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Wallet
import org.web3j.crypto.WalletFile
import timber.log.Timber
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.security.spec.ECGenParameterSpec


class Web3Manager(
    private val pictionKeyStore: PictionKeyStore,
    private val dynamicPreference: DynamicPreference,
    private val cipherHelper: CipherHelper
) :
    Web3 {

    private val walletCount = "walletCount"
    private val pictionAlias = "com.piction.pixelwallet"
    private var web3: Web3 = Web3j()

    override fun getVersion(result: (String) -> Unit) {
        web3.getVersion(result)
    }

    fun createWallet() {
        if (dynamicPreference.get(walletCount, 0) == 0) {
            dynamicPreference.put(walletCount, 1)
        }
        val count = dynamicPreference.get(walletCount, 0)

        val keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "SC")
        val ecGenParameterSpec = ECGenParameterSpec("secp256k1")
        keyPairGenerator.initialize(ecGenParameterSpec, SecureRandom())
        Timber.d("createWallet")
        val keyPair = keyPairGenerator.genKeyPair()
        Timber.d(keyPair.private.encoded.toString())

        pictionKeyStore.putKeyPair("pixelwallet_$count", keyPair)
        dynamicPreference.put(walletCount, count + 1)
    }

    fun getWalletCount(): Int =
        pictionKeyStore.getAliasList().size

    fun getWalletFile(index: Int): WalletFile {
        Timber.d("getWalletFile")
        Timber.d(pictionKeyStore.getKeyPair(pictionKeyStore.getAliasList()[index]).private.toString())
        return Wallet.createLight(
            cipherHelper.decrypt(dynamicPreference.get(pictionAlias, "")),
            ECKeyPair.create(pictionKeyStore.getKeyPair(pictionKeyStore.getAliasList()[index])))
    }

}