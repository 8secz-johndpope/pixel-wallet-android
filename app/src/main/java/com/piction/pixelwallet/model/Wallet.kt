package com.piction.pixelwallet.model

import java.io.File


data class Wallet(
    val address: Address,
    val name: String,
    val file: File
)