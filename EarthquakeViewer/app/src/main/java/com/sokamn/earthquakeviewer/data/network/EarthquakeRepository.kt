package com.sokamn.earthquakeviewer.data.network

import android.util.Log
import com.sokamn.earthquakeviewer.data.network.model.EarthquakeJsonResponse
import com.sokamn.earthquakeviewer.data.network.model.EarthquakeUseCase
import com.sokamn.earthquakeviewer.data.db.EarthquakeDB
import com.sokamn.earthquakeviewer.model.Earthquake
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EarthquakeRepository(private val db: EarthquakeDB) {
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
                    db.earthquakeDao.insertAll(earthquakeList)
                }
            }else{
                Log.d("ERRESPONSE", finishCall.errorBody().toString())
            }
            earthquakeList
        }
    }

    fun getEarthquakeByDB(): MutableList<Earthquake>{
        return db.earthquakeDao.getAll()
    }

    companion object {
        const val PER_DAY = 0
        const val PER_WEEK = 1
        const val PER_MOUNTH = 2
    }

}
