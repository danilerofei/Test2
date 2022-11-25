package com.test.test2.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BasketData(
    @SerializedName("id") val id: Long,
    @SerializedName("images") val imageUrl: String,
    @SerializedName("price") val price: Long,
    @SerializedName("title") val title: String,
    @Expose var count: Int = 1
) {

    companion object {

        @JvmStatic
        fun fromProductData(data: SingleProductInfo): BasketData {
            return BasketData(
                id = data.id,
                imageUrl = data.images.firstOrNull() ?: "",
                price = data.price,
                title = data.title,
                count = 1
            )
        }
    }
}