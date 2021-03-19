package com.acompany.lift.features.routines.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acompany.lift.data.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RoutineScreenViewModel @Inject constructor(
    appRepository: AppRepository
) : ViewModel() {

    val allRoutines = appRepository
        .getRoutines()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}