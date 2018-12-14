package com.piction.pixelwallet.lib.persistence.db.mapper

import com.piction.pixelwallet.lib.persistence.db.entities.PixelWallet
import com.piction.pixelwallet.model.Account
import com.piction.pixelwallet.model.Address
import java.io.File


object PixelWalletMapper {
    fun accountToEntity(account: Account): PixelWallet =
        PixelWallet(account.address.toString(), account.name, account.file.path)

    fun entityToAccount(pixelWallet: PixelWallet): Account =
        Account(Address(pixelWallet.address), pixelWallet.name, File(pixelWallet.path))
}