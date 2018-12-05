package com.piction.pixelwallet

import android.app.Activity
import android.app.Application
import com.piction.pixelwallet.lib.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

        super.onCreate()
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector
}