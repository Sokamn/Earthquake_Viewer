package com.sokamn.earthquakeviewer

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EarthquakeRepository {
    private val useCase = EarthquakeUseCase()
     internal suspend fun getEarthquakes(id: Int) : MutableList<Earthquake>{
        return withContext(Dispatchers.IO){
            val call = service
            var finishCall = call.getEarthquakesPerDay()
            var earthquakeList = mutableListOf<Earthquake>()
            when(id){
                PER_DAY ->{
                    finishCall =  call.getEarthquakesPerDay()
                }
                PER_WEEK ->{
                    finishCall =  call.getEarthquakesPerWeek()
                }
                PER_MOUNTH ->{
                    finishCall =  call.getEarthquakesPerMounth()
                }
            }
            if (finishCall.isSuccessful){
                val response: EarthquakeJsonResponse? = finishCall.body()
                earthquakeList.clear()
                if (response?.features?.isNotEmpty() == true){
                    earthquakeList = useCase.parseCallResponse(response)
                }
            }else{
                Log.d("ERRESPONSE", finishCall.errorBody().toString())
            }
            earthquakeList
        }
    }

    companion object {
        const val PER_DAY = 0
        const val PER_WEEK = 1
        const val PER_MOUNTH = 2
    }

}
