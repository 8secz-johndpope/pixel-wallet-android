package com.piction.pixelwallet.lib.di.modules

import android.content.Context
import com.piction.pixelwallet.lib.keystore.FileBasedWalletKeyStore
import com.piction.pixelwallet.lib.persistence.preferences.SecureDynamicPreference
import com.piction.pixelwallet.lib.web3.Web3Manager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class Web3Module {

    @Provides
    @Singleton
    fun provideFileBaseWalletKeyStore(context: Context): FileBasedWalletKeyStore =
        FileBasedWalletKeyStore(context)

    @Provides
    @Singleton
    fun provideWeb3Manager(
        fileBasedWalletKeyStore: FileBasedWalletKeyStore,
        secureDynamicPreference: SecureDynamicPreference
    ): Web3Manager =
        Web3Manager(fileBasedWalletKeyStore, secureDynamicPreference)

}