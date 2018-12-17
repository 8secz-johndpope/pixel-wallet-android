package com.piction.pixelwallet.ui.adapter.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.piction.pixelwallet.R
import com.piction.pixelwallet.model.Address
import com.piction.pixelwallet.model.Wallet
import kotlinx.android.synthetic.main.item_wallet.view.*
import java.io.File


class WalletPagerAdapter : PagerAdapter() {

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
        } else {
            view = LayoutInflater.from(container.context).inflate(R.layout.item_wallet, container, false).apply {
                name.text = item.address.hex
            }
        }
        container.addView(view, 0)
        return view
    }

    override fun getPageWidth(position: Int): Float {
        return if (position == items.size - 1) 1f else 0.95f
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