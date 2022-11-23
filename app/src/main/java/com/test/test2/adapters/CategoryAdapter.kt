package com.test.test2.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.test.test2.R
import com.test.test2.dagger.BaseAdapter
import com.test.test2.data.CategoryData
import com.test.test2.databinding.ItemCategoryBinding
import com.test.test2.interfaces.IClickListener

class CategoryAdapter(
    context: Context, items: List<CategoryData>,
    private val clickListener: IClickListener
) : BaseAdapter<CategoryData, ItemCategoryBinding>(context, items) {

    override fun bind(data: CategoryData, binding: ItemCategoryBinding, position: Int) {
        binding.tvCatTitle.text = data.title
        binding.ivCatImage.setImageResource(data.image)
        if (data.isSelected) {
            val color = ContextCompat.getColor(context, R.color.color1)
            binding.tvCatTitle.setTextColor(color)
            binding.ivCatImage.drawable.setTint(Color.WHITE)
            val back = ContextCompat.getDrawable(context, R.drawable.splash_circle)?.apply {
                setTint(color)
            }
            binding.constraintLayout.background = back
        } else {
            binding.tvCatTitle.setTextColor(Color.BLACK)
            val color = ContextCompat.getColor(context, R.color.color_icon)
            binding.ivCatImage.drawable.setTint(color)
            val back = ContextCompat.getDrawable(context, R.drawable.category_circle)?.apply {
                setTint(Color.WHITE)
            }
            binding.constraintLayout.background = back
        }
        binding.constraintLayout.setOnClickListener {
            clickListener.onClick(position)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ItemCategoryBinding {
        return ItemCategoryBinding.inflate(inflater, container, false)
    }
}