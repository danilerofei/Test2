package com.test.test2.dagger

import android.content.Context
import com.test.test2.utils.ApiService
import com.test.test2.utils.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiService::class])
interface AppComponent {

    fun context(): Context

    fun applicationContext(): Context

    fun inject(activity: InjectActivity)
}