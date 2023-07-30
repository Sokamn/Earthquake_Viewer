package com.sokamn.earthquakeviewer.data.remote

import com.sokamn.earthquakeviewer.data.remote.response.EarthquakeDto
import retrofit2.Response
import retrofit2.http.GET

interface EarthquakeApi {

    @GET("all_month.geojson")
    suspend fun fetchEarthquakesPerMounth(): EarthquakeDto

    @GET(value = "all_week.geojson")
    suspend fun fetchEarthquakesPerWeek(): EarthquakeDto

    @GET(value = "all_day.geojson")
    suspend fun fetchEarthquakesPerDay(): EarthquakeDto
}