package com.acompany.lift.features.sessions.data

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acompany.lift.data.AppRepository
import com.acompany.lift.data.model.Session
import com.acompany.lift.di.AppModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionDetailViewModel @Inject constructor(
    private val appRepository: AppRepository,
    @AppModule.AppCoroutineScope val appCoroutineScope: CoroutineScope,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val sessionId = savedStateHandle.get<Long>("sessionId")!!

    val screenState = appRepository.getSessions(listOf(sessionId))
        .mapNotNull { routines -> routines.firstOrNull() }
        .map { session -> ScreenState.Ready(session) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ScreenState.Loading)

    fun deleteSession(session: Session) {
        appCoroutineScope.launch { // Use AppCoroutineScope here, as ViewModel may go out of scope during delete
            appRepository.deleteSession(session)
        }
    }
}