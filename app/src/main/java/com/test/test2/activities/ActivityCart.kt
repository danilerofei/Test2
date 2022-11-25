package com.test.test2.activities

import androidx.recyclerview.widget.LinearLayoutManager
import com.test.test2.R
import com.test.test2.adapters.AdapterCartItems
import com.test.test2.dagger.BaseApp
import com.test.test2.dagger.BindingActivity
import com.test.test2.data.BasketData
import com.test.test2.databinding.ActivityCartBinding
import com.test.test2.interfaces.ICartCallback
import com.test.test2.models.ApiModel
import java.util.*

class ActivityCart : BindingActivity<ActivityCartBinding>() {

    private val apiModel by lazy {
        modelProvider[ApiModel::class.java]
    }

    private val adapter by lazy {
        AdapterCartItems(this, (application as BaseApp).baskets, object : ICartCallback {
            override fun onPlus(data: BasketData) {
                addCount(data)
            }

            override fun onMinus(data: BasketData) {
                if (data.count == 1) {
                    onRemove(data)
                } else {
                    minusCount(data)
                }
            }

            override fun onRemove(data: BasketData) {
                removeBasket(data)
            }
        })
    }

    private fun addCount(data: BasketData) {
        val pos = adapter.items.indexOf(data)
        if (pos >= 0) {
            val items = adapter.items.toMutableList()
            data.count += 1
            items[pos] = data
            adapter.items = items
            adapter.notifyItemChanged(pos)

            val appPos = (application as BaseApp).baskets.indexOf(data)
            if (appPos >= 0) {
                val newItems = (application as BaseApp).baskets
                newItems[appPos] = data
                (application as BaseApp).baskets = newItems
            }

            calculateTotalPrice()
        }
    }

    private fun minusCount(data: BasketData) {
        val pos = adapter.items.indexOf(data)
        if (pos >= 0) {
            val items = adapter.items.toMutableList()
            data.count -= 1
            items[pos] = data
            adapter.items = items
            adapter.notifyItemChanged(pos)

            val appPos = (application as BaseApp).baskets.indexOf(data)
            if (appPos >= 0) {
                val newItems = (application as BaseApp).baskets
                newItems[appPos] = data
                (application as BaseApp).baskets = newItems
            }

            calculateTotalPrice()
        }
    }

    private fun removeBasket(data: BasketData) {
        val pos = adapter.items.indexOf(data)
        if (pos >= 0) {
            val items = adapter.items.toMutableList()
            items.removeAt(pos)
            adapter.items = items
            adapter.notifyItemRemoved(pos)

            val appPos = (application as BaseApp).baskets.indexOf(data)
            if (appPos >= 0) {
                val newItems = (application as BaseApp).baskets
                newItems.removeAt(appPos)
                (application as BaseApp).baskets = newItems
            }

            calculateTotalPrice()
        }
    }

    override fun onCreate() {
        apiModel.loadCartData()
        apiModel.cartData.observe(this) {
            binding.tvDelivery.text = it.delivery

            val items = adapter.items.toMutableList()
            it.basket.forEach { d ->
                items.add(d.apply { count = 1 })
            }
            adapter.updateItems(items)

            calculateTotalPrice()
        }

        binding.rvCartItems.adapter = adapter
        binding.rvCartItems.layoutManager = LinearLayoutManager(this)

        binding.fabBack.setOnClickListener {
            onBack()
        }
    }

    private fun calculateTotalPrice() {
        var totalPrice = 0L
        adapter.items.forEach { d ->
            totalPrice += (d.price * d.count)
        }
        val totalPriceS = String.format(Locale.US, "%,d", totalPrice)
        binding.tvTotalPrice.text = getString(R.string.total_price, totalPriceS)
    }

    override fun getViewBinding(): ActivityCartBinding {
        return ActivityCartBinding.inflate(layoutInflater)
    }
}