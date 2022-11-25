package com.test.test2.data

import com.google.gson.annotations.SerializedName

data class ProductData(
    @SerializedName("id") val id: Long,
    @SerializedName("is_new") val isNew: Boolean,
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String,
    @SerializedName("picture") val pictureUrl: String,
    @SerializedName("is_buy") val isBuy: Boolean,
    @SerializedName("is_favorites") val isFav: Boolean,
    @SerializedName("price_without_discount") val price: Long,
    @SerializedName("discount_price") val oldPrice: Long
) {
    constructor(id: Long, title: String, price: Long, oldPrice: Long) : this(
        id,
        false,
        title,
        "",
        "https://avatars.mds.yandex.net/get-mpic/5251502/img_id6904501887217302707.jpeg/orig",
        false,
        false,
        price,
        oldPrice
    )
}