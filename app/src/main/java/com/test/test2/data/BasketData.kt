package com.test.test2.data

import com.google.gson.annotations.SerializedName

data class BasketData(
    @SerializedName("id") val id: Long,
    @SerializedName("images") val imageUrl: String,
    @SerializedName("price") val price: Long,
    @SerializedName("title") val title: String
)