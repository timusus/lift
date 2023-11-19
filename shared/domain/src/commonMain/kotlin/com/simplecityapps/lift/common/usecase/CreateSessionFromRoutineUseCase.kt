package com.simplecityapps.lift.common.usecase

import com.simplecityapps.lift.common.model.Routine
import com.simplecityapps.lift.common.model.toSession
import com.simplecityapps.lift.common.repository.SessionRepository

class CreateSessionFromRoutineUseCase(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(
        routine: Routine
    ): String {
        val session = routine.toSession()
        sessionRepository.upsertSession(session = session)
        session.sessionExercises.forEach { sessionExercise ->
            sessionRepository.upsertSessionExercise(sessionExercise)
        }
        sessionRepository.updateSession(
            session.copy(currentExercise = session.sessionExercises.firstOrNull())
        )
        return session.id
    }
}