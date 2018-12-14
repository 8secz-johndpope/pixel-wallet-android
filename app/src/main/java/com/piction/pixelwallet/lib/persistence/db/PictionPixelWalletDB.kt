package com.piction.pixelwallet.lib.persistence.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.piction.pixelwallet.lib.persistence.db.dao.PixelWalletDAO
import com.piction.pixelwallet.lib.persistence.db.entities.PixelWallet


@Database(entities = [PixelWallet::class], version = 1)
abstract class PictionPixelWalletDB : RoomDatabase() {

    abstract fun pixelWalletDAO(): PixelWalletDAO
}