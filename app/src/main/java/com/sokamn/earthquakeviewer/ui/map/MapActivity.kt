package com.sokamn.earthquakeviewer.ui.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sokamn.earthquakeviewer.R
import com.sokamn.earthquakeviewer.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMapBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
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

    }
}