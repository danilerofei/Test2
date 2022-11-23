package com.test.test2.dagger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.test.test2.utils.ApiService
import com.test.test2.utils.ModelFactory
import javax.inject.Inject

abstract class InjectActivity : AppCompatActivity() {

    @Inject
    lateinit var apiService: ApiService

    val modelProvider by lazy {
        ViewModelProvider(this, ModelFactory(apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as BaseApp).appComponent.inject(this)
    }
}