package com.piction.pixelwallet.ui.adapter.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.piction.pixelwallet.R
import com.piction.pixelwallet.databinding.ItemCreateWalletBinding
import com.piction.pixelwallet.databinding.ItemWalletBinding
import com.piction.pixelwallet.model.Address
import com.piction.pixelwallet.model.Wallet

import java.io.File


class WalletPagerAdapter(private val delegate: Delegate) : PagerAdapter() {

    interface Delegate {
        fun onClickAccountAddress(wallet: Wallet)
        fun onClickManagement(wallet: Wallet)
        fun onClickCreateWallet()
        fun onClickLoadWallet()
    }

    private var items = mutableListOf<Wallet>()

    private fun getCreateItemData(): Wallet = Wallet(Address("empty"), "empty", File(""))

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view == any
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = items[position]
        val view: View

        if (item.address.cleanHex == "empty") {
            view = LayoutInflater.from(container.context).inflate(R.layout.item_create_wallet, container, false)
            DataBindingUtil.bind<ItemCreateWalletBinding>(view)?.run {
                wallet = item
                this.delegate = this@WalletPagerAdapter.delegate
            }
        } else {
            view = LayoutInflater.from(container.context).inflate(R.layout.item_wallet, container, false)
            DataBindingUtil.bind<ItemWalletBinding>(view)?.run {
                wallet = item
                this.delegate = this@WalletPagerAdapter.delegate
            }
        }

        container.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as View)
    }

    override fun getItemPosition(any: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    fun updateItems(items: List<Wallet>) {
        this.items = items.toMutableList()
        this.items.add(getCreateItemData())

        notifyDataSetChanged()
    }
}