package com.piction.pixelwallet.lib.di

import com.piction.pixelwallet.App
import com.piction.pixelwallet.lib.di.modules.ActivityModule
import com.piction.pixelwallet.lib.di.modules.ApplicationModule
import com.piction.pixelwallet.lib.di.modules.Web3Module
import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    ActivityModule::class,
    Web3Module::class])
interface AppComponent : AndroidInjector<App> {

override fun inject(instance: App)

@Component.Builder
interface Builder {
    @BindsInstance
    fun application(application: App): AppComponent.Builder

    fun build(): AppComponent
}
}