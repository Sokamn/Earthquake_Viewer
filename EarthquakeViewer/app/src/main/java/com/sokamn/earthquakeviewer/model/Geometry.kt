package com.sokamn.earthquakeviewer.model

data class Geometry (val coordinates: List<Double>){
        val longitude: Double
        get() = coordinates[0]
        val latitude: Double
        get() = coordinates[1]
}