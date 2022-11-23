package com.test.test2.utils

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.test.test2.data.CartData
import com.test.test2.data.MainData
import com.test.test2.data.SingleProductInfo
import dagger.Module
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

@Module
class ApiService @Inject constructor() {

    private val client by lazy {
        OkHttpClient()
    }

    private val gson by lazy {
        GsonBuilder().create()
    }

    suspend fun getMainData(): MainData? {
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url("https://run.mocky.io/v3/654bd15e-b121-49ba-a588-960956b15175")
                    .build()

                val str = client.newCall(request).execute().body.string()
                val type = object : TypeToken<MainData>() {}.type
                return@withContext gson.fromJson<MainData>(str, type)
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
    }

    suspend fun getSingleData(): SingleProductInfo? {
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url("https://run.mocky.io/v3/6c14c560-15c6-4248-b9d2-b4508df7d4f5")
                    .build()

                val str = client.newCall(request).execute().body.string()
                val type = object : TypeToken<SingleProductInfo>() {}.type
                return@withContext gson.fromJson<SingleProductInfo>(str, type)
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
    }

    suspend fun loadCartData(): CartData? {
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url("https://run.mocky.io/v3/53539a72-3c5f-4f30-bbb1-6ca10d42c149")
                    .build()

                val str = client.newCall(request).execute().body.string()
                val type = object : TypeToken<CartData>() {}.type
                return@withContext gson.fromJson<CartData>(str, type)
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
    }
}