package com.sokamn.earthquakeviewer

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sokamn.earthquakeviewer.EarthquakeRepository.Companion.PER_DAY
import kotlinx.coroutines.*
import java.net.UnknownHostException

class EarthquakeViewModel: ViewModel() {
    private val repository = EarthquakeRepository()
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