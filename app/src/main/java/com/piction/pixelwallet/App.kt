package com.piction.pixelwallet

import android.app.Activity
import android.app.Application
import com.piction.pixelwallet.lib.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
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

        Timber.plant(Timber.DebugTree())
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector
}