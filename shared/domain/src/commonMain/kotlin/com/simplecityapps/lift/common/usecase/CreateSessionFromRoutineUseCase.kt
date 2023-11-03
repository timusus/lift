package com.simplecityapps.lift.common.usecase

import com.simplecityapps.lift.common.model.Routine
import com.simplecityapps.lift.common.model.toSession
import com.simplecityapps.lift.common.repository.SessionRepository

class CreateSessionFromRoutineUseCase(val sessionRepository: SessionRepository) {
    suspend operator fun invoke(
        routine: Routine
    ): Long {
        val session = routine.toSession()
        val sessionId = sessionRepository.createSession(
            session = session,
            generateId = true
        )
        val sessionExerciseIdMap = session.sessionExercises.map { sessionExercise ->
            sessionRepository.createSessionExercise(sessionExercise.copy(sessionId = sessionId)) to sessionExercise
        }
        sessionExerciseIdMap.firstOrNull()
            ?.let { (sessionExerciseId, sessionExercise) ->
                sessionRepository.updateSession(
                    session.copy(
                        id = sessionId,
                        currentExercise = sessionExercise.copy(
                            id = sessionExerciseId
                        )
                    )
                )
            }
        return sessionId
    }
}