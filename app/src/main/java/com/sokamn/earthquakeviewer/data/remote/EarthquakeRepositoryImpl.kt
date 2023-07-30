package com.sokamn.earthquakeviewer.data.remote

import com.sokamn.earthquakeviewer.data.mappers.toDomain
import com.sokamn.earthquakeviewer.domain.model.Earthquake
import com.sokamn.earthquakeviewer.domain.repository.EarthquakeRepository
import com.sokamn.earthquakeviewer.domain.util.AppConstants
import com.sokamn.earthquakeviewer.domain.util.Resource
import javax.inject.Inject

class EarthquakeRepositoryImpl @Inject constructor(
    private val earthquakeApi: EarthquakeApi
): EarthquakeRepository {
    override suspend fun fetchEarthquakes(frequency: Int): Resource<List<Earthquake>> {
        return try{
            val earthquakeList = mutableListOf<Earthquake>()
            when(frequency){
                AppConstants.PER_DAY->{
                    earthquakeApi.fetchEarthquakesPerDay().features.forEach { feature->
                        earthquakeList.add(feature.toDomain())
                    }
                }
                AppConstants.PER_WEEK->{
                    earthquakeApi.fetchEarthquakesPerWeek().features.forEach { feature->
                        earthquakeList.add(feature.toDomain())
                    }
                }
                AppConstants.PER_MOUNTH->{
                    earthquakeApi.fetchEarthquakesPerMounth().features.forEach { feature->
                        earthquakeList.add(feature.toDomain())
                    }
                }
            }
            Resource.Success(
                data = earthquakeList
            )
        } catch (e: Exception){
            Resource.Error(
                errorMessage = "Unknown Error"
            )
        }
    }
}