package com.test.test2.data

import androidx.annotation.DrawableRes

data class CategoryData(
    var title: String,
    @DrawableRes var image: Int,
    var isSelected: Boolean = false
) {
}