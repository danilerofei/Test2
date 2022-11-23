package com.test.test2.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.test.test2.R
import com.test.test2.dagger.BaseAdapter
import com.test.test2.data.CapacityCheck
import com.test.test2.databinding.ItemCapacityBinding
import com.test.test2.interfaces.IClickListener

class AdapterCapacity(
    context: Context,
    items: List<CapacityCheck>,
    private val onClick: IClickListener
) : BaseAdapter<CapacityCheck, ItemCapacityBinding>(context, items) {

    override fun bind(data: CapacityCheck, binding: ItemCapacityBinding, position: Int) {
        if (data.isChecked) {
            val back = ContextCompat.getDrawable(context, R.drawable.selected_item_capacity)
            binding.content.background = back
            binding.tvCapacity.setTextColor(Color.WHITE)
        } else {
            binding.content.background = null
            binding.tvCapacity.setTextColor(Color.parseColor("#8D8D8D"))
        }

        binding.tvCapacity.text = context.getString(R.string.capacity, data.capacity)
        binding.content.setOnClickListener {
            onClick.onClick(position)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ItemCapacityBinding {
        return ItemCapacityBinding.inflate(inflater, container, false)
    }
}