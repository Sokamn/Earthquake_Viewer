package com.sokamn.earthquakeviewer.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sokamn.earthquakeviewer.core.Event
import com.sokamn.earthquakeviewer.domain.model.Earthquake
import com.sokamn.earthquakeviewer.domain.usecase.GetEarthquakeUseCase
import com.sokamn.earthquakeviewer.domain.util.AppConstants
import com.sokamn.earthquakeviewer.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getEarthquakeUseCase: GetEarthquakeUseCase
) : ViewModel() {

    private val _earthquakeSelected = MutableLiveData<Event<Earthquake>>()
    val earthquakeSelected: LiveData<Event<Earthquake>>
        get() = _earthquakeSelected

    private val _earthquakeState = MutableLiveData<Resource<List<Earthquake>>>()
    val earthquakeState: LiveData<Resource<List<Earthquake>>>
        get() = _earthquakeState

    init{
        getEarthquake(AppConstants.PER_DAY)
    }


    fun getEarthquake(frequency: Int){
        viewModelScope.launch {
            getEarthquakeUseCase(frequency).onEach {
                _earthquakeState.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun onEarthquakeSelected(earthquake: Earthquake){
        _earthquakeSelected.value = Event(earthquake)
    }

}