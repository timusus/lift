package com.acompany.lift.features.sessions.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acompany.lift.common.DateFormatter
import com.acompany.lift.data.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SessionScreenViewModel @Inject constructor(
    appRepository: AppRepository,
    val dateFormatter: DateFormatter
) : ViewModel() {

    val screenState: StateFlow<ScreenState> = appRepository
        .getSessions()
        .mapNotNull { sessions -> ScreenState.Ready(sessions) }
        .stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = ScreenState.Loading)
}