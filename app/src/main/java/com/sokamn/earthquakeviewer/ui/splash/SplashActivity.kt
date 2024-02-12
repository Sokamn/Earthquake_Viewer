package com.sokamn.earthquakeviewer.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import com.bumptech.glide.Glide.init
import com.sokamn.earthquakeviewer.R
import com.sokamn.earthquakeviewer.databinding.ActivitySplashBinding
import com.sokamn.earthquakeviewer.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val splashViewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        setUIComponents()
        initObservers()
        initLooper()
    }

    private fun initLooper() {
        Handler(Looper.getMainLooper()).postDelayed({
            splashViewModel.onTimePast()
        }, 3000)
    }

    private fun initObservers() {
        splashViewModel.navigateToMain.observe(this) {
            it.getContentIfNotHandled()?.let {
                goToMain()
            }
        }
    }

    private fun setUIComponents() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        val animation = AnimationSet(false)
        val fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        animation.addAnimation(fadeIn)
        binding.imvLogo.animation = animation

    }

    private fun goToMain() {
        finish()
        startActivity(MainActivity.create(this))
    }
}