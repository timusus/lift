package com.simplecityapps.lift.android.ui.sessions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplecityapps.lift.common.model.Session
import com.simplecityapps.lift.common.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.Clock
import javax.inject.Inject
import kotlin.time.Duration.Companion.days

sealed class SessionListViewState {
    data object Loading : SessionListViewState()
    data class Ready(val groupedSessions: Map<SessionGroup, List<Session>>) : SessionListViewState()
}

enum class SessionGroup {
    LastWeek, LastMonth, LastYear, Older
}

@HiltViewModel
class SessionListViewModel @Inject constructor(
    sessionRepository: SessionRepository
) : ViewModel() {

    val viewState = sessionRepository.getSessions()
        .map { sessions ->
            val grouped = sessions
                .filter { it.isComplete }
                .groupBy { session ->
                    val now = Clock.System.now()
                    when (session.startDate) {
                        in now.minus(7.days)..now -> SessionGroup.LastWeek
                        in now.minus(30.days)..now -> SessionGroup.LastMonth
                        in now.minus(365.days)..now -> SessionGroup.LastYear
                        else -> SessionGroup.Older
                    }
                }
            SessionListViewState.Ready(grouped)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = SessionListViewState.Loading
        )
}