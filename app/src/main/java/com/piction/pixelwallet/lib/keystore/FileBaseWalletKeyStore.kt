package com.piction.pixelwallet.lib.keystore

import android.content.Context
import com.piction.pixelwallet.lib.persistence.preferences.SecureDynamicPreference
import com.piction.pixelwallet.model.Account
import com.piction.pixelwallet.model.Address
import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.WalletUtils
import java.io.File
import java.util.*


class FileBasedWalletKeyStore(val context: Context, private val secureDynamicPreference: SecureDynamicPreference) {

    val pwdKey = "piction_pixelwallet"

    init {
        if (secureDynamicPreference.get(pwdKey, "").isEmpty()) {
            secureDynamicPreference.put(pwdKey, UUID.randomUUID().toString())
        }
    }

    private val keyStoreDirectory by lazy {
        File(context.filesDir, "keystore").also {
            it.mkdirs()
        }
    }

    fun putKey(key: ECKeyPair): Account =
        WalletUtils.generateWalletFile(secureDynamicPreference.get(pwdKey, ""), key, keyStoreDirectory, false).let {
            Account(Address(it.split("--").last().removeSuffix(".json")), "", getFile(it))
        }

    fun deleteKey(account: Account) = account.file.delete()

    fun getCredentials(account: Account): Credentials =
        WalletUtils.loadCredentials(secureDynamicPreference.get(pwdKey, ""), account.file)

    private fun getFile(name: String): File {
        return File(keyStoreDirectory.absolutePath + "/" + name)
    }
}