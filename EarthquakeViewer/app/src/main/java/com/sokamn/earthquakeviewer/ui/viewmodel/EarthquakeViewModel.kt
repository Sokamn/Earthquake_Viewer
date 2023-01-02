package com.sokamn.earthquakeviewer.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.sokamn.earthquakeviewer.data.db.getDatabase
import com.sokamn.earthquakeviewer.data.network.ApiResponseStatus
import com.sokamn.earthquakeviewer.data.network.EarthquakeRepository
import com.sokamn.earthquakeviewer.data.network.EarthquakeRepository.Companion.PER_DAY
import com.sokamn.earthquakeviewer.model.Earthquake
import kotlinx.coroutines.*
import java.net.UnknownHostException

class EarthquakeViewModel(app: Application): AndroidViewModel(app) {
    private val db = getDatabase(app)
    private val repository = EarthquakeRepository(db)
    private val mList = MutableLiveData<MutableList<Earthquake>>()
    val list: LiveData<MutableList<Earthquake>>
        get() = mList
    private val mStatus = MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus>
        get() = mStatus


    init{
        viewModelScope.launch {
            try {
                mStatus.value = ApiResponseStatus.LOADING
                mList.value = repository.getEarthquakes(PER_DAY)
                mStatus.value = ApiResponseStatus.SUCCESS
            }catch (e: UnknownHostException){
                Log.i("ERROR", "no hay internet | $e")
                mStatus.value = ApiResponseStatus.ERROR
                mList.value = repository.getEarthquakeByDB()
            }

        }
    }

    fun getXEarthquake(id: Int){
        viewModelScope.launch {
            mList.value?.clear()
            mList.value = repository.getEarthquakes(id)
        }
    }

}