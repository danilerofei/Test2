package com.test.test2.dagger

import android.app.Application
import com.test.test2.utils.AppModule

class BaseApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}