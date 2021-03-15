package com.acompany.lift.features.routines.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acompany.lift.data.AppRepository
import com.acompany.lift.data.model.Routine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineScreenViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    val allRoutines: MutableStateFlow<List<Routine>> = MutableStateFlow(emptyList())

    init {
        viewModelScope.launch {
            appRepository.getAllRoutines().collect { routines ->
                allRoutines.emit(routines)
            }
        }
    }
}