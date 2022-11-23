package com.test.test2.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.test.test2.R
import com.test.test2.dagger.BaseAdapter
import com.test.test2.data.ColorCheck
import com.test.test2.databinding.ItemColorBinding
import com.test.test2.interfaces.IClickListener

class AdapterColors(
    context: Context,
    items: List<ColorCheck>,
    private val onClick: IClickListener
) : BaseAdapter<ColorCheck, ItemColorBinding>(context, items) {

    override fun bind(data: ColorCheck, binding: ItemColorBinding, position: Int) {
        if (data.isChecked) {
            binding.ivCheck.setImageResource(R.drawable.ic_baseline_check_24)
        } else {
            binding.ivCheck.setImageDrawable(null)
        }
        val back = ContextCompat.getDrawable(context, R.drawable.splash_circle)?.apply {
            setTint(Color.parseColor(data.color))
        }
        binding.ivCheck.background = back
        binding.content.setOnClickListener {
            onClick.onClick(position)
        }
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): ItemColorBinding {
        return ItemColorBinding.inflate(inflater, container, false)
    }
}