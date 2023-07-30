package com.sokamn.earthquakeviewer.data.remote.response

data class FeaturesDto (
    val id: String,
    val properties: PropertiesDto,
    val geometry: GeometryDto
)