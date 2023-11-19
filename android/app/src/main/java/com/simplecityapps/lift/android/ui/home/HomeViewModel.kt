package com.simplecityapps.lift.android.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplecityapps.lift.android.common.AsyncState
import com.simplecityapps.lift.common.model.Session
import com.simplecityapps.lift.common.repository.AuthRepository
import com.simplecityapps.lift.common.repository.SessionRepository
import com.simplecityapps.lift.common.usecase.ImportDataUseCase
import com.simplecityapps.lift.common.usecase.SyncDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    sessionRepository: SessionRepository,
    private val authRepository: AuthRepository,
    private val syncDataUseCase: SyncDataUseCase,
    private val importDataUseCase: ImportDataUseCase
) : ViewModel() {

    val authState = authRepository.authState

    val viewState: StateFlow<AsyncState<List<Session>>> = sessionRepository.getSessions(
        sessionIds = null
    )
        .map { sessions ->
            AsyncState.Success(
                sessions
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = AsyncState.Loading
        )

    private val _syncing = MutableStateFlow(false)
    val syncing: StateFlow<Boolean> = _syncing.asStateFlow()

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }

    fun importData() {
        viewModelScope.launch {
            importDataUseCase()
        }
    }

    fun syncData() {
        viewModelScope.launch {
            _syncing.update { true }
            syncDataUseCase()
            _syncing.update { false }
        }
    }
}