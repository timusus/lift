package com.simplecityapps.lift.android.ui.routines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplecityapps.lift.common.model.Routine
import com.simplecityapps.lift.common.model.Session
import com.simplecityapps.lift.common.repository.RoutineRepository
import com.simplecityapps.lift.common.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed class RoutineListViewState {
    data object Loading : RoutineListViewState()
    data class Ready(val routineSessionMap: List<Pair<Routine, Session?>>, val leastRecentRoutine: Routine) : RoutineListViewState()
}

@HiltViewModel
class RoutineListViewModel @Inject constructor(
    routineRepository: RoutineRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    val viewState = routineRepository.getRoutines(null)
        .map { routines ->

            val routineSessionMap = routines.map { routine -> routine to routine.latestSession() }

            // First routine with no recent session
            val leastRecentRoutine = routineSessionMap.firstOrNull { routineSessionPair -> routineSessionPair.second == null }?.first // First routine with no session
                ?: routineSessionMap.firstOrNull { it.second?.isComplete == false }?.first // First routine whose session is incomplete
                ?: routineSessionMap.minByOrNull { routineSessionPair -> routineSessionPair.second!!.endDate ?: routineSessionPair.second!!.startDate }?.first // First routine whose completion date is oldest
                ?: routineSessionMap.first().first // First routine

            RoutineListViewState.Ready(
                routineSessionMap = routineSessionMap,
                leastRecentRoutine = leastRecentRoutine
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = RoutineListViewState.Loading
        )

    private suspend fun Routine.latestSession(): Session? {
        return sessionRepository.getLatestSessionForRoutine(this.id).first()
    }
}