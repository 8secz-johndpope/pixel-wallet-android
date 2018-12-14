package com.piction.pixelwallet.lib.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.piction.pixelwallet.App
import com.piction.pixelwallet.lib.keystore.FileBasedWalletKeyStore
import com.piction.pixelwallet.lib.persistence.CurrentUser
import com.piction.pixelwallet.lib.persistence.db.PictionPixelWalletDB
import com.piction.pixelwallet.lib.persistence.db.dao.PixelWalletDAO
import com.piction.pixelwallet.lib.persistence.preferences.DynamicPreference
import com.piction.pixelwallet.lib.persistence.preferences.SecureDynamicPreference
import com.piction.pixelwallet.lib.persistence.repository.PixelWalletRepository
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
    internal fun provideCipherHelper(context: Context): CipherHelper =
        CipherHelper(context)


    @Provides
    @Singleton
    internal fun provideSecureDynamicPreference(
        sharedPreferences: SharedPreferences,
        cipherHelper: CipherHelper
    ): SecureDynamicPreference =
        SecureDynamicPreference(sharedPreferences, cipherHelper)


    @Provides
    @Singleton
    internal fun providePictionPixelWalletDB(context: Context): PictionPixelWalletDB =
        Room.databaseBuilder(context, PictionPixelWalletDB::class.java, "pixelwallet.db")
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    internal fun providePixelWalletDAO(db: PictionPixelWalletDB): PixelWalletDAO =
        db.pixelWalletDAO()


    @Provides
    @Singleton
    internal fun providePixelWalletRepository(
        context: Context,
        pixelWalletDAO: PixelWalletDAO,
        fileBasedWalletKeyStore: FileBasedWalletKeyStore
    ): PixelWalletRepository =
        PixelWalletRepository(context, pixelWalletDAO, fileBasedWalletKeyStore)

    @Provides
    @Singleton
    internal fun provideCurrentUser() =
        CurrentUser()

}