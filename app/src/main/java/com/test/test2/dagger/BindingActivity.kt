package com.test.test2.dagger

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.viewbinding.ViewBinding
import com.test.test2.interfaces.IViewBindingActivity

abstract class BindingActivity<VB : ViewBinding> : InjectActivity(), IViewBindingActivity<VB> {

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)

        onCreate()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBack()
            }
        })
    }

    abstract fun onCreate()

    fun onBack() {
        finish()
    }
}