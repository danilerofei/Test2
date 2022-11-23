package com.test.test2.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue

object ViewUtils {

    fun getThemeAccentColor(context: Context): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(com.google.android.material.R.attr.colorAccent, value, true)
        return value.data
    }

    fun updateDrawableColor(drawable: Drawable?, color: Int) {
        if (drawable == null) return
        drawable.setTint(color)
    }
}