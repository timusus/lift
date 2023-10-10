package com.simplecityapps.lift.repository

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.simplecityapps.lift.model.Session
import com.simplecityapps.lift.model.SessionExercise
import com.simplecityapps.shuttle.logging.LogPriority
import com.simplecityapps.shuttle.logging.logcat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class SessionManager(
    private val sessionRepository: SessionRepository
) {

    private val _restStartTime = MutableStateFlow<Instant?>(null)

    @NativeCoroutinesState
    val restStartTime = _restStartTime.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val restDuration = _restStartTime.flatMapLatest { startTime ->
        (0..Int.MAX_VALUE).asFlow()
            .map {
                startTime?.let { Clock.System.now().minus(startTime) }
            }
            .onEach { delay(1000) }
            .onStart {
                startTime?.let { emit(Clock.System.now().minus(startTime)) }
            }
    }

    private fun setResting(resting: Boolean) {
        if (resting) {
            _restStartTime.update { Clock.System.now() }
        } else {
            _restStartTime.update { null }
        }
    }

    @NativeCoroutines
    suspend fun updateProgress(session: Session) {
        if (session.isComplete) {
            logcat(priority = LogPriority.ASSERT) { "exercise complete, returning" }
            return
        }

        if (_restStartTime.value != null) {
            setResting(false)
            logcat { "resting. Returning" }
            return
        } else {
            setResting(true)
        }

        logcat { "Advancing exercise" }
        val currentExercise = advanceExercise(
            sessionExercise = session.currentExercise ?: session.sessionExercises.first()
        )

        if (currentExercise.isComplete) {
            logcat { "Advancing session" }
            advanceSession(
                session = session.copy(
                    currentExercise = currentExercise,
                    sessionExercises = session.sessionExercises.map { if (it.id == currentExercise.id) currentExercise else it }
                )
            )
        }
    }

    /**
     * Advances the current set for the [SessionExercise].
     * Set the exercise end date if the final set is completed.
     */
    @NativeCoroutines
    suspend fun advanceExercise(sessionExercise: SessionExercise): SessionExercise {
        if (sessionExercise.sets == 0) {
            throw IllegalStateException("Failed to advance set: Exercise contains no sets")
        }

        var currentSet = sessionExercise.currentSet
        var exerciseEndDate = sessionExercise.endDate
        if (currentSet < sessionExercise.sets - 1) {
            logcat { "Incrementing set to ${currentSet + 1}" }
            currentSet += 1
        } else {
            logcat { "Sets complete. Setting end date" }
            exerciseEndDate = Clock.System.now()
        }

        val newSessionExercise = sessionExercise.copy(
            currentSet = currentSet,
            endDate = exerciseEndDate
        )
        sessionRepository.updateSessionExercise(newSessionExercise)

        return newSessionExercise
    }

    /**
     * Advances the current exercise for the [Session].
     * Set the session end date if the final exercise is completed.
     */
    @NativeCoroutines
    suspend fun advanceSession(session: Session): Session {
        if (session.sessionExercises.isEmpty()) {
            throw IllegalStateException("Failed to advance session: Session contains no exercises")
        }

        var sessionEndDate = session.endDate
        var currentExercise = requireNotNull(session.currentExercise)

        if (session.sessionExercises.all { sessionExercise -> sessionExercise.isComplete }) {
            logcat { "All exercises complete. Setting session end date" }
            sessionEndDate = Clock.System.now()
        } else {
            logcat { "Updating sessions' current exercise.. was $currentExercise to ${session.sessionExercises.first { !it.isComplete }}" }
            currentExercise =
                session.sessionExercises.first { sessionExercise -> !sessionExercise.isComplete }
        }

        val newSession = session.copy(
            currentExercise = currentExercise,
            endDate = sessionEndDate
        )
        sessionRepository.updateSession(newSession)

        return newSession
    }
}