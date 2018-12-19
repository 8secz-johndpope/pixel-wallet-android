package com.piction.pixelwallet.model

import java.io.File
import java.math.BigInteger


class WalletCard(
    address: Address,
    name: String,
    file: File,
    val amount: BigInteger = BigInteger("0")
) : Wallet(address, name, file) {

}