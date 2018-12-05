package com.piction.pixelwallet.lib.di.modules

import com.piction.pixelwallet.lib.web3.Web3Manager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class Web3Module {

    @Provides
    @Singleton
    fun provideWeb3Manager(): Web3Manager = Web3Manager(Web3Manager.Web3Type.WEB3J)

}