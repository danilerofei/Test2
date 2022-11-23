package com.test.test2.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.test.test2.R
import com.test.test2.dagger.BaseAdapter
import com.test.test2.data.ProductData
import com.test.test2.databinding.ItemBestSellerBinding
import com.test.test2.interfaces.IClickListener
import java.util.*

class BestSellerAdapter(
    context: Context,
    items: List<ProductData>,
    private val onIClickListener: IClickListener
) : BaseAdapter<ProductData, ItemBestSellerBinding>(context, items) {

    override fun bind(data: ProductData, binding: ItemBestSellerBinding, position: Int) {
        binding.tvName.text = data.title
        val price = String.format(Locale.US, "%,d", data.price)
        val oldPrice = String.format(Locale.US, "%,d", data.oldPrice)
        binding.tvPrice.text = context.getString(R.string.price, price)
        binding.oldPrice.apply {
            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            text = context.getString(R.string.price, oldPrice)
        }
        Glide.with(context)
            .load(data.pictureUrl)
            .into(binding.ivImg)

        binding.content.setOnClickListener {
            onIClickListener.onClick(position)
        }

        val fabBack = if (data.isFav) {
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_24)
        } else {
            ContextCompat.getDrawable(context, R.drawable.favorite)
        }
        val likeColor = Color.parseColor("#FF6E4E")
        val draw = fabBack?.apply {
            setTint(likeColor)
        }
        binding.ivFav.setImageDrawable(draw)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ItemBestSellerBinding {
        return ItemBestSellerBinding.inflate(inflater, container, false)
    }
}