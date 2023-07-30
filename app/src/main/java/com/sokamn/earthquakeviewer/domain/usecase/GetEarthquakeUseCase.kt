package com.sokamn.earthquakeviewer.domain.usecase

import com.sokamn.earthquakeviewer.domain.model.Earthquake
import com.sokamn.earthquakeviewer.domain.repository.EarthquakeRepository
import com.sokamn.earthquakeviewer.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetEarthquakeUseCase @Inject constructor(
    private val earthquakeRepository: EarthquakeRepository
){
    suspend operator fun invoke(frequency: Int): Flow<Resource<List<Earthquake>>> = flow{
        emit(Resource.Loading())

        when(val networkRequest = earthquakeRepository.fetchEarthquakes(frequency)){
            is Resource.Success -> emit(networkRequest)
            is Resource.Error -> emit(Resource.Error(networkRequest.message.toString()))
            else -> Resource.Finished
        }
    }
}