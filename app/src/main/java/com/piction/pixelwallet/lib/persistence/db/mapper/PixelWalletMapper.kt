package com.piction.pixelwallet.lib.persistence.db.mapper

import com.piction.pixelwallet.lib.persistence.db.entities.PixelWallet
import com.piction.pixelwallet.model.Wallet
import com.piction.pixelwallet.model.Address
import java.io.File


object PixelWalletMapper {
    fun accountToEntity(wallet: Wallet): PixelWallet =
        PixelWallet(wallet.address.toString(), wallet.name, wallet.file.path)

    fun entityToAccount(pixelWallet: PixelWallet): Wallet =
        Wallet(Address(pixelWallet.address), pixelWallet.name, File(pixelWallet.path))
}