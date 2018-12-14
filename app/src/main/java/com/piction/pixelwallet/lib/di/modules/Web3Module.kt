package com.piction.pixelwallet.lib.di.modules

import android.content.Context
import com.piction.pixelwallet.lib.keystore.FileBasedWalletKeyStore
import com.piction.pixelwallet.lib.persistence.preferences.SecureDynamicPreference
import com.piction.pixelwallet.lib.persistence.repository.PixelWalletRepository
import com.piction.pixelwallet.lib.web3.Web3Manager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class Web3Module {

    @Provides
    @Singleton
    internal fun provideFileBaseWalletKeyStore(
        context: Context,
        secureDynamicPreference: SecureDynamicPreference
    ): FileBasedWalletKeyStore =
        FileBasedWalletKeyStore(context, secureDynamicPreference)

    @Provides
    @Singleton
    internal fun provideWeb3Manager(): Web3Manager =
        Web3Manager()

}