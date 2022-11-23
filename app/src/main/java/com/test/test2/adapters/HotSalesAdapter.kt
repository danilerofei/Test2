package com.test.test2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.test.test2.dagger.BaseAdapter
import com.test.test2.data.ProductData
import com.test.test2.databinding.ItemHotSalesBinding

class HotSalesAdapter(
    context: Context,
    items: List<ProductData>
) : BaseAdapter<ProductData, ItemHotSalesBinding>(context, items) {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ItemHotSalesBinding {
        return ItemHotSalesBinding.inflate(inflater, container, false)
    }

    override fun bind(data: ProductData, binding: ItemHotSalesBinding, position: Int) {
        binding.tvTitle.text = data.title
        binding.tvSubtitle.text = data.subtitle
        Glide.with(context)
            .load(data.pictureUrl)
            .into(binding.ivImg)

        binding.rlNew.isVisible = data.isNew
    }
}