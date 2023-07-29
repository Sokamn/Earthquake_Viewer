package com.sokamn.earthquakeviewer.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sokamn.earthquakeviewer.core.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

): ViewModel(){

    private val _navigateToMain = MutableLiveData<Event<Boolean>>()
    val navigateToMain: LiveData<Event<Boolean>>
        get() = _navigateToMain

    fun onTimePast(){
        _navigateToMain.value = Event(true)
    }

}