package com.piction.pixelwallet.ui.view.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.piction.pixelwallet.R
import com.piction.pixelwallet.databinding.ActivityHomeBinding
import com.piction.pixelwallet.model.Wallet
import com.piction.pixelwallet.ui.adapter.viewpager.WalletPagerAdapter
import com.piction.pixelwallet.ui.view.wallet.CreateWalletActivity
import com.piction.pixelwallet.util.extension.observeLiveData
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import timber.log.Timber
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(HomeActivityViewModel::class.java)
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayUseLogoEnabled(true)
            setLogo(R.drawable.appbar)
            setDisplayShowTitleEnabled(false)
        }

        viewSet()

        observeLiveData(viewModel.startActivity) { startActivity<CreateWalletActivity>() }
        observeLiveData(viewModel.walletList) {
            (viewPager.adapter as WalletPagerAdapter).updateItems(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                toast("setting click and create wallet")
                viewModel.createWallet()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun viewSet() {
        viewPager.pageMargin = getDp(10)
        viewPager.adapter = WalletPagerAdapter(object: WalletPagerAdapter.Delegate{
            override fun onClickAccountAddress(wallet: Wallet) {
                toast("onClickAccountAddress: ${wallet.address}")
            }

            override fun onClickManagement(wallet: Wallet) {
                toast("onClickManagement: ${wallet.address}")
            }

            override fun onClickCreateWallet() {
                toast("onClickCreateWallet")
            }

            override fun onClickLoadWallet() {
                toast("onClickLoadWallet")
            }

        })
        ptr_layout.setOnRefreshListener {
            home_recyclerView.stopScroll()
            //todo transfer log call
            ptr_layout.isRefreshing = false
        }
    }

    private fun getDp(size: Int): Int {
        val scale = resources.displayMetrics.density
        return (size * scale + 0.5f).toInt()
    }
}
