package com.sokamn.earthquakeviewer.data.network.model

import com.sokamn.earthquakeviewer.model.Features

data class EarthquakeJsonResponse (
    val features: List<Features>
        )