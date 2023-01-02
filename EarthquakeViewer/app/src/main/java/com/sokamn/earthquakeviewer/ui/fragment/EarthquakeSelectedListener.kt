package com.sokamn.earthquakeviewer.ui.fragment

import com.sokamn.earthquakeviewer.model.Earthquake

interface EarthquakeSelectedListener {
    fun onEarthquakeSelected(earthquake: Earthquake)
}