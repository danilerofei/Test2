package com.test.test2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.test.test2.R
import com.test.test2.dagger.BaseAdapter
import com.test.test2.data.BasketData
import com.test.test2.databinding.ItemCartItemBinding
import com.test.test2.interfaces.ICartCallback

class AdapterCartItems(
    context: Context,
    items: List<BasketData>,
    private val cartCallback: ICartCallback,
) : BaseAdapter<BasketData, ItemCartItemBinding>(context, items) {

    override fun bind(data: BasketData, binding: ItemCartItemBinding, position: Int) {
        binding.tvTitle.text = data.title
        Glide.with(context)
            .load(data.imageUrl)
            .into(binding.ivImg)

        val totalPrice = data.price * data.count
        binding.tvPrice.text = context.getString(R.string.price, totalPrice.toString())

        binding.tvCounter.text = data.count.toString()

        binding.ivMinus.setOnClickListener {
            cartCallback.onMinus(data)
        }
        binding.ivPlus.setOnClickListener {
            cartCallback.onPlus(data)
        }
        binding.ivRemove.setOnClickListener {
            cartCallback.onRemove(data)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): ItemCartItemBinding {
        return ItemCartItemBinding.inflate(inflater, container, false)
    }
}