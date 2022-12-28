package com.sokamn.earthquakeviewer

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EarthquakeViewModelFactory(private val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EarthquakeViewModel(app) as T
    }
}