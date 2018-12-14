package com.piction.pixelwallet.lib.persistence.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class PixelWallet(
    @PrimaryKey
    val address: String,
    val name: String,
    val path: String
)