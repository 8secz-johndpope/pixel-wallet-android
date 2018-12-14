package com.piction.pixelwallet.lib.persistence.db.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.piction.pixelwallet.lib.persistence.db.entities.PixelWallet

@Dao
interface PixelWalletDAO {

    @Query("SELECT * FROM PixelWallet")
    fun selectWalletList(): LiveData<List<PixelWallet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWallet(pixelWallet: PixelWallet)

    @Query("DELETE FROM PixelWallet WHERE address = :address")
    fun deleteWallet(address: String)


}