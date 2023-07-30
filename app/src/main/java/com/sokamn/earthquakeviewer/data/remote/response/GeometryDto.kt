package com.sokamn.earthquakeviewer.data.remote.response

data class GeometryDto (val coordinates: List<Double>){
    val longitude: Double
        get() = coordinates[0]
    val latitude: Double
        get() = coordinates[1]
}