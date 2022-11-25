package com.test.test2.dagger

import android.app.Application
import com.test.test2.data.BasketData
import com.test.test2.utils.AppModule

class BaseApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    var baskets: MutableList<BasketData> = mutableListOf()
}