package com.sokamn.earthquakeviewer.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.sokamn.earthquakeviewer.ui.MainActivity

class SplashActivity : AppCompatActivity() {
    private val animation = AnimationSet(false)
    private lateinit var imvLogo: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash)
        imvLogo = this.findViewById(R.id.imvLogo)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        val fadeIn: Animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        animation.addAnimation(fadeIn)
        imvLogo.animation = animation
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }
}