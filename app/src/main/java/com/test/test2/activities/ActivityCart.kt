package com.test.test2.activities

import androidx.recyclerview.widget.LinearLayoutManager
import com.test.test2.R
import com.test.test2.adapters.AdapterCartItems
import com.test.test2.dagger.BindingActivity
import com.test.test2.databinding.ActivityCartBinding
import com.test.test2.models.ApiModel
import java.util.*

class ActivityCart : BindingActivity<ActivityCartBinding>() {

    private val apiModel by lazy {
        modelProvider[ApiModel::class.java]
    }

    private val adapter by lazy {
        AdapterCartItems(this, emptyList())
    }

    override fun onCreate() {
        apiModel.loadCartData()
        apiModel.cartData.observe(this) {
            binding.tvDelivery.text = it.delivery
            val totalPrice = String.format(Locale.US, "%,d", it.totalPrice)
            binding.tvTotalPrice.text = getString(R.string.total_price, totalPrice)

            adapter.updateItems(it.basket)
        }

        binding.rvCartItems.adapter = adapter
        binding.rvCartItems.layoutManager = LinearLayoutManager(this)

        binding.fabBack.setOnClickListener {
            onBack()
        }
    }

    override fun getViewBinding(): ActivityCartBinding {
        return ActivityCartBinding.inflate(layoutInflater)
    }
}