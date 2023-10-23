package com.simplecityapps.lift.android.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplecityapps.lift.android.DataImportUseCase
import com.simplecityapps.lift.android.common.AsyncState
import com.simplecityapps.lift.android.ui.auth.AuthManager
import com.simplecityapps.lift.model.Session
import com.simplecityapps.lift.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authManager: AuthManager,
    sessionRepository: SessionRepository,
    private val dataImportUseCase: DataImportUseCase,
) : ViewModel() {

    val authState = authManager.authState

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

    fun importData() {
        viewModelScope.launch {
            dataImportUseCase()
        }
    }
}