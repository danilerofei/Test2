package com.test.test2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.test.test2.R
import com.test.test2.dagger.BaseAdapter
import com.test.test2.data.BasketData
import com.test.test2.databinding.ItemCartItemBinding

class AdapterCartItems(
    context: Context,
    items: List<BasketData>
) : BaseAdapter<BasketData, ItemCartItemBinding>(context, items) {

    override fun bind(data: BasketData, binding: ItemCartItemBinding, position: Int) {
        binding.tvTitle.text = data.title
        Glide.with(context)
            .load(data.imageUrl)
            .into(binding.ivImg)

        binding.tvPrice.text = context.getString(R.string.price, data.price.toString())

        binding.tvCounter.text = "1"
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ItemCartItemBinding {
        return ItemCartItemBinding.inflate(inflater, container, false)
    }
}