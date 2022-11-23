package com.test.test2.interfaces

import android.graphics.Typeface


interface IBubbleNavigation {

    fun setNavigationChangeListener(navigationChangeListener: IBubbleNavigationChangeListener)

    fun setTypeface(typeface: Typeface?)

    fun getCurrentActiveItemPosition(): Int

    fun setCurrentActiveItem(position: Int)

    fun setBadgeValue(position: Int, value: String?)
}