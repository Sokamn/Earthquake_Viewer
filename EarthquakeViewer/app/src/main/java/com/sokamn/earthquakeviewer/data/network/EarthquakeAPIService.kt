package com.sokamn.earthquakeviewer.data.network

import com.sokamn.earthquakeviewer.data.network.model.EarthquakeJsonResponse
import com.sokamn.earthquakeviewer.data.network.EarthquakeAPIService.Companion.URL_EARTHQUAKE
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface EarthquakeAPIService {
    @GET(value = "all_month.geojson")
    suspend fun getEarthquakesPerMounth(): Response<EarthquakeJsonResponse>
    @GET(value = "all_week.geojson")
    suspend fun getEarthquakesPerWeek(): Response<EarthquakeJsonResponse>
    @GET(value = "all_day.geojson")
    suspend fun getEarthquakesPerDay(): Response<EarthquakeJsonResponse>

    companion object {
        const val URL_EARTHQUAKE = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/"
    }
}

private var retrofit = Retrofit.Builder()
        .baseUrl(URL_EARTHQUAKE)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
var service = retrofit.create(EarthquakeAPIService::class.java)


