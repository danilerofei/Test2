package com.test.test2.data

import com.google.gson.annotations.SerializedName

data class CartData(
    @SerializedName("basket") val basket: List<BasketData>,
    @SerializedName("delivery") val delivery: String,
    @SerializedName("id") val id: Long,
    @SerializedName("total") val totalPrice: Long
)