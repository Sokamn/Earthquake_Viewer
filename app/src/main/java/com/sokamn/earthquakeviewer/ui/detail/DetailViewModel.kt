package com.sokamn.earthquakeviewer.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sokamn.earthquakeviewer.core.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(): ViewModel(

){

    private val _navigateToMapActivity = MutableLiveData<Event<Boolean>>()
    val navigateToMapActivity: LiveData<Event<Boolean>>
        get() = _navigateToMapActivity

    fun onPlaceHolderGPSSelected(){
        _navigateToMapActivity.value = Event(true)
    }
}