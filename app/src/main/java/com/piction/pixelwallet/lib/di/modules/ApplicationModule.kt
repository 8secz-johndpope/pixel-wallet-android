package com.piction.pixelwallet.lib.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.piction.pixelwallet.App
import com.piction.pixelwallet.lib.persistence.preferences.DynamicPreference
import com.piction.pixelwallet.lib.persistence.preferences.SecureDynamicPreference
import com.piction.pixelwallet.lib.secure.CipherHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    fun provideContext(application: App): Context =
        application

    @Provides
    @Singleton
    internal fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences("com.piction.pixelwallet", Context.MODE_PRIVATE)


    @Provides
    @Singleton
    internal fun provideDynamicPreference(sharedPreferences: SharedPreferences): DynamicPreference =
        DynamicPreference(sharedPreferences)


    @Provides
    @Singleton
    internal fun provideCipherHelper(context: Context) : CipherHelper =
        CipherHelper(context)


    @Provides
    @Singleton
    internal fun provideSecureDynamicPreference(
        sharedPreferences: SharedPreferences,
        cipherHelper: CipherHelper
    ): SecureDynamicPreference =
        SecureDynamicPreference(sharedPreferences, cipherHelper)
    

    //todo Room DB
}