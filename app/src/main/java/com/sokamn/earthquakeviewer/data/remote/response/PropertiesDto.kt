package com.sokamn.earthquakeviewer.data.remote.response

data class PropertiesDto (
    val mag: Double,
    val place: String,
    val time: Long,
    val url: String,
    val tsunami: Int,
    val magType: String
)