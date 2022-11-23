package com.test.test2.activities

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.test.test2.dagger.BindingActivity
import com.test.test2.databinding.ActivitySplashBinding

class ActivitySplash : BindingActivity<ActivitySplashBinding>() {

    companion object {

        private const val splashDelay = 500L
    }

    override fun getViewBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, ActivityMain::class.java)
            startActivity(intent)
            finish()
        }, splashDelay)
    }
}