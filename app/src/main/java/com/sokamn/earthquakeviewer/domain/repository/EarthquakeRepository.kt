package com.sokamn.earthquakeviewer.domain.repository

import com.sokamn.earthquakeviewer.domain.model.Earthquake
import com.sokamn.earthquakeviewer.domain.util.Resource

interface EarthquakeRepository {

    suspend fun fetchEarthquakes(frequency: Int): Resource<List<Earthquake>>
}