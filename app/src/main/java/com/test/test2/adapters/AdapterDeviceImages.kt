package com.test.test2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.test.test2.dagger.BaseAdapter
import com.test.test2.databinding.ItemDeviceImageBinding

class AdapterDeviceImages(
    context: Context,
    items: List<String>
) : BaseAdapter<String, ItemDeviceImageBinding>(context, items) {

    override fun bind(data: String, binding: ItemDeviceImageBinding, position: Int) {
        Glide.with(context)
            .load(data)
            .into(binding.ivImg)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ItemDeviceImageBinding {
        return ItemDeviceImageBinding.inflate(inflater, container, false)
    }
}