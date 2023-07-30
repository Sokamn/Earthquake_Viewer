package com.sokamn.earthquakeviewer.data.mappers

import com.sokamn.earthquakeviewer.data.remote.response.EarthquakeDto
import com.sokamn.earthquakeviewer.data.remote.response.FeaturesDto
import com.sokamn.earthquakeviewer.domain.model.Earthquake

fun FeaturesDto.toDomain(): Earthquake{
    return Earthquake(this.id,properties.place,properties.magType,properties.mag,properties.time,geometry.latitude, geometry.longitude, properties.tsunami,properties.url)
}