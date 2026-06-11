package com.example.johan_coco

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.johan_coco.Onboarding.OnboardingActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPref = getSharedPreferences("BinaDesaPref", Context.MODE_PRIVATE)
            val onboardingFinished = sharedPref.getBoolean("onboardingFinished", false)
            val isLogin = sharedPref.getBoolean("isLogin", false)

            when {
                !onboardingFinished -> {
                    startActivity(Intent(this, OnboardingActivity::class.java))
                }
                isLogin -> {
                    startActivity(Intent(this, BaseActivity::class.java))
                }
                else -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
            finish()
        }, 3000)
    }
}