package com.sokamn.earthquakeviewer.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sokamn.earthquakeviewer.R
import com.sokamn.earthquakeviewer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent =
            Intent(context, MainActivity::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        setUIComponents()
        initObservers()
    }

    private fun initObservers() {

    }

    private fun setUIComponents() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }
}