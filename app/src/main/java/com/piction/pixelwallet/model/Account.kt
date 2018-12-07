package com.piction.pixelwallet.model


data class Account(val network: String, val address: String, val secret: String, val path: String)