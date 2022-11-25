package com.test.test2.interfaces

import com.test.test2.data.BasketData

interface ICartCallback {

    fun onPlus(data: BasketData)

    fun onMinus(data: BasketData)

    fun onRemove(data: BasketData)
}