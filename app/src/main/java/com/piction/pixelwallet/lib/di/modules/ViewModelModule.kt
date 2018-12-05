package com.piction.pixelwallet.lib.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.piction.pixelwallet.lib.di.PixelViewModelFactory
import com.piction.pixelwallet.lib.di.ViewModelKey
import com.piction.pixelwallet.ui.view.home.HomeActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: PixelViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeActivityViewModel::class)
    internal abstract fun bindHomeActivityViewModel(viewModel: HomeActivityViewModel): ViewModel

}