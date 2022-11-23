package com.test.test2.activities

import android.view.View
import com.test.test2.R
import com.test.test2.dagger.BindingActivity
import com.test.test2.databinding.ActivityMainBinding
import com.test.test2.fragments.FragmentHome
import com.test.test2.interfaces.IBubbleNavigationChangeListener
import com.test.test2.models.ApiModel

class ActivityMain : BindingActivity<ActivityMainBinding>() {

    val apiModel by lazy {
        modelProvider[ApiModel::class.java]
    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    private val fragmentHome by lazy {
        FragmentHome()
    }

    override fun onCreate() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, fragmentHome)
            .commit()

        binding.navHost.setNavigationChangeListener(object : IBubbleNavigationChangeListener {

            override fun onNavigationChanged(view: View?, position: Int) {

            }
        })
    }
}