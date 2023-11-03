package com.simplecityapps.lift.android.ui.sessions.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplecityapps.lift.android.common.AsyncState
import com.simplecityapps.lift.android.common.SoundManager
import com.simplecityapps.lift.common.model.Routine
import com.simplecityapps.lift.common.model.Session
import com.simplecityapps.lift.common.model.SessionExercise
import com.simplecityapps.lift.common.repository.RoutineRepository
import com.simplecityapps.lift.common.repository.SessionRepository
import com.simplecityapps.lift.common.usecase.CreateSessionFromRoutineUseCase
import com.simplecityapps.lift.repository.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.ZERO
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class SessionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val routineRepository: RoutineRepository,
    private val sessionRepository: SessionRepository,
    private val sessionManager: SessionManager,
    private val createSessionUseCase: CreateSessionFromRoutineUseCase,
    private val soundManager: SoundManager
) : ViewModel() {

    private val routineId: Long = requireNotNull(savedStateHandle["routineId"]) {
        "Routine ID must not be null"
    }

    private val sessionId = MutableStateFlow<Long?>(null)

    private val _useExistingSessionDialogState = MutableStateFlow<DialogSessionState>(DialogSessionState.Hide)
    val useExistingSessionDialogState = _useExistingSessionDialogState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val session = sessionId
        .filterNotNull()
        .flatMapLatest { sessionId ->
            sessionRepository.getSession(sessionId)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    private val restDuration = combine(
        session.filterNotNull(),
        sessionManager.restDuration
    ) { session, restDuration ->
        restDuration?.let {
            session.currentExercise?.routineExercise?.restPeriod?.seconds?.let { restPeriod ->
                (restPeriod - restDuration).coerceAtLeast(ZERO)
            }
        }
    }.distinctUntilChanged()

    val sessionState =
        combine(
            session.filterNotNull(),
            sessionManager.restStartTime,
        )
        { session, restStartTime ->
            AsyncState.Success(
                SessionDetailViewState(
                    session = session,
                    restStartTime = restStartTime,
                    restDuration = restDuration
                )
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = AsyncState.Loading
        )

    init {
        viewModelScope.launch {
            val routine = routineRepository.getRoutine(routineId).first()

            val existingSession = getExistingSession(routine)

            if (existingSession != null) {
                sessionId.update { existingSession.id }
                _useExistingSessionDialogState.emit(DialogSessionState.Show(existingSession))
            } else {
                createNewSession(routine)
            }
        }

        restDuration.filterNotNull()
            .onEach { duration ->
                if (duration <= ZERO) {
                    playRestTimerTone()
                }
            }.launchIn(viewModelScope)
    }

    private suspend fun getExistingSession(routine: Routine): Session? {
        return sessionRepository.getSessionsForRoutine(routine.id)
            .first()
            .firstOrNull { session -> !session.isComplete }
    }

    fun createNewSession(routine: Routine) {
        _useExistingSessionDialogState.update { DialogSessionState.Hide }
        viewModelScope.launch {
            sessionId.update {
                createSessionUseCase(
                    routine = routine
                )
            }
        }
    }

    fun useExistingSession() {
        _useExistingSessionDialogState.update { DialogSessionState.Hide }
    }

    fun updateProgress(session: Session) {
        viewModelScope.launch {
            sessionManager.updateProgress(
                session = session
            )
        }
    }

    fun updateSessionExerciseWeight(sessionExercises: SessionExercise, weight: Float?) {
        viewModelScope.launch {
            sessionRepository.updateSessionExercise(
                sessionExercise = sessionExercises.copy(weight = weight)
            )
        }
    }

    private fun playRestTimerTone() {
        soundManager.playSound(SoundManager.Sound.Tone)
    }

    suspend fun deleteSession(session: Session) {
        sessionRepository.deleteSession(session)
    }
}
