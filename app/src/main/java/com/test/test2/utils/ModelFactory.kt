package com.test.test2.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.test2.models.ApiModel

@Suppress("UNCHECKED_CAST")
class ModelFactory(
    private val apiService: ApiService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ApiModel::class.java)) {
            return ApiModel(apiService) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}