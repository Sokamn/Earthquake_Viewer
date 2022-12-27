package com.sokamn.earthquakeviewer

class EarthquakeUseCase {
    internal fun parseCallResponse(call: EarthquakeJsonResponse): MutableList<Earthquake> {
        val list = mutableListOf<Earthquake>()
        call.features.forEach { feature ->
            val properties = feature.properties
            val geometry = feature.geometry
            list.add(Earthquake(feature.id,properties.place,properties.magType,properties.mag,properties.time,geometry.latitude, geometry.longitude, properties.tsunami,properties.url))
        }
        return list
    }
}