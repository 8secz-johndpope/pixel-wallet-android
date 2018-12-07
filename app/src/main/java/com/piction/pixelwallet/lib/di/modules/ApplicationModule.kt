package com.piction.pixelwallet.lib.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.piction.pixelwallet.App
import com.piction.pixelwallet.lib.persistence.preferences.StringDynamicPreference
import com.piction.pixelwallet.model.User
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
    internal fun provideStringDynamicPreference(sharedPreferences: SharedPreferences): StringDynamicPreference =
        StringDynamicPreference(sharedPreferences, null)

    @Provides
    @Singleton
    internal fun provideUser(stringDynamicPreference: StringDynamicPreference): User =
        User(stringDynamicPreference)


    //todo Account
    //todo FileUtil

    //todo Room DB
}