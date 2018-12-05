package com.piction.pixelwallet.lib.di.modules

import com.piction.pixelwallet.ui.view.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeHomeActivity(): HomeActivity

}