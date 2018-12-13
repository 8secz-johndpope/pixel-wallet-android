package com.piction.pixelwallet.lib.di.modules

import com.piction.pixelwallet.lib.persistence.preferences.DynamicPreference
import com.piction.pixelwallet.lib.secure.CipherHelper
import com.piction.pixelwallet.lib.secure.PictionKeyStore
import com.piction.pixelwallet.lib.web3.Web3Manager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class Web3Module {

    @Provides
    @Singleton
    fun provideWeb3Manager(
        pictionKeyStore: PictionKeyStore,
        dynamicPreference: DynamicPreference,
        cipherHelper: CipherHelper
    ): Web3Manager =
        Web3Manager(pictionKeyStore, dynamicPreference, cipherHelper)

}