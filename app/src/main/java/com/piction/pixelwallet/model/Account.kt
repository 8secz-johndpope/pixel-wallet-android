package com.piction.pixelwallet.model

import java.io.File


data class Account(
    val address: Address,
    val name: String,
    val file: File
)