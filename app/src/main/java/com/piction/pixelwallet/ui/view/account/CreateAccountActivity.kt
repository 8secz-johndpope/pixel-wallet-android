package com.piction.pixelwallet.ui.view.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.piction.pixelwallet.R
import com.piction.pixelwallet.databinding.ActivityCreateAccountBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class CreateAccountActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(CreateAccountActivityViewModel::class.java)
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityCreateAccountBinding>(this, R.layout.activity_create_account)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
    }
}
