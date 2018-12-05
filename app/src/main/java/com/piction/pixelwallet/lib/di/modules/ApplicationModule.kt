package com.piction.pixelwallet.lib.di.modules

import android.content.Context
import com.piction.pixelwallet.App
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun provideContext(application: App): Context =
        application

    //todo ViewModelModule
    //todo fragmentModule
    
    //todo Account
    //todo FileUtil

    //todo Room DB
}