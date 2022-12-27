package com.sokamn.earthquakeviewer

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sokamn.earthquakeviewer.EarthquakeRepository.Companion.PER_DAY
import kotlinx.coroutines.*

class EarthquakeViewModel: ViewModel() {
    private val repository = EarthquakeRepository()
    private val mList = MutableLiveData<MutableList<Earthquake>>()
    val list: LiveData<MutableList<Earthquake>>
        get() = mList

    init{
        viewModelScope.launch {
                mList.value = repository.getEarthquakes(PER_DAY)
        }
    }

    fun getXEarthquake(id: Int){
        viewModelScope.cancel()
        viewModelScope.launch {
            mList.value?.clear()
            mList.value = repository.getEarthquakes(id)
        }
    }

}