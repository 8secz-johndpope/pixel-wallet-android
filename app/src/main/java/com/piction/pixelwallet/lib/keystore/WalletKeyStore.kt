package com.piction.pixelwallet.lib.keystore

import com.piction.pixelwallet.model.Address
import org.web3j.crypto.ECKeyPair


interface WalletKeyStore {
    fun importKey(key: ECKeyPair, password: String): Address?
    fun deleteKey(address: Address): Boolean
    fun getKeyForAddress(address: Address, password: String): ECKeyPair?
    fun hasKeyForForAddress(wallethAddress: Address): Boolean
    fun getAddresses(): Set<Address>
}