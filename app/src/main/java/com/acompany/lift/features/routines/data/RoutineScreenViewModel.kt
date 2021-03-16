package com.acompany.lift.features.routines.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acompany.lift.data.AppRepository
import com.acompany.lift.data.model.Routine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineScreenViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _allRoutines: MutableStateFlow<List<Routine>> = MutableStateFlow(emptyList())
    val allRoutines: StateFlow<List<Routine>> = _allRoutines

    init {
        viewModelScope.launch {
            appRepository.getAllRoutines().collect { routines ->
                _allRoutines.emit(routines)
            }
        }
    }
}