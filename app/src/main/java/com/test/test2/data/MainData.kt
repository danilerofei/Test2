package com.test.test2.data

import com.google.gson.annotations.SerializedName

data class MainData(
    @SerializedName("home_store") val homeStore: List<ProductData>,
    @SerializedName("best_seller") val bestSeller: List<ProductData>
)