package com.test.test2.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.test2.data.CartData
import com.test.test2.data.MainData
import com.test.test2.data.SingleProductInfo
import com.test.test2.utils.ApiService
import kotlinx.coroutines.launch

class ApiModel(
    private val apiService: ApiService
) : ViewModel() {

    val mainData = MutableLiveData<MainData>()

    val productData = MutableLiveData<SingleProductInfo>()

    val cartData = MutableLiveData<CartData>()

    init {
        viewModelScope.launch {
            mainData.value = apiService.getMainData()
        }
    }

    fun loadProductInfo() = viewModelScope.launch {
        productData.value = apiService.getSingleData()
    }

    fun loadCartData() = viewModelScope.launch {
        cartData.value = apiService.loadCartData()
    }
}