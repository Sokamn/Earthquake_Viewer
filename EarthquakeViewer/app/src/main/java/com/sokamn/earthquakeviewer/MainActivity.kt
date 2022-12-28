package com.sokamn.earthquakeviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import com.sokamn.earthquakeviewer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), EarthquakeSelectedListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR


    }

    override fun onEarthquakeSelected(earthquake: Earthquake) {
        findNavController(R.id.fcvConteiner).navigate(ListFragmentDirections.actionListFragmentToDetailFragment(earthquake))
    }
}