package com.test.test2.data

import com.google.gson.annotations.SerializedName

data class SingleProductInfo(
    @SerializedName("CPU") val cpu: String,
    @SerializedName("camera") val camera: String,
    @SerializedName("capacity") val capacity: List<String>,
    @SerializedName("color") val colors: List<String>,
    @SerializedName("id") val id: Long,
    @SerializedName("images") val images: List<String>,
    @SerializedName("isFavorites") val isFav: Boolean,
    @SerializedName("price") val price: Long,
    @SerializedName("rating") val rating: Float,
    @SerializedName("sd") val sd: String,
    @SerializedName("ssd") val ssd: String,
    @SerializedName("title") val title: String
) {
}