package com.simplecityapps.lift.repository

import com.simplecityapps.lift.common.database.AppDataSource
import com.simplecityapps.lift.common.model.Session
import com.simplecityapps.lift.common.model.SessionExercise
import com.simplecityapps.lift.common.repository.RoutineRepository
import com.simplecityapps.lift.common.repository.SessionRepository
import com.simplecityapps.lift.model.toSession
import com.simplecityapps.lift.model.toSessionExercise
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class DefaultSessionRepository(
    private val dataSource: AppDataSource,
    private val routineRepository: RoutineRepository
) : SessionRepository {

    // Session Exercises

     override suspend fun createSessionExercise(
         sessionExercise: SessionExercise,
         generateId: Boolean
     ): Long {
        return dataSource.createSessionExercise(
            id = if (generateId) null else sessionExercise.id,
            sets = sessionExercise.sets,
            reps = sessionExercise.reps,
            weight = sessionExercise.weight,
            sessionId = sessionExercise.sessionId,
            routineExerciseId = sessionExercise.routineExercise.id
        )
    }

     override fun getSessionExercises(sessionIds: Collection<Long>?): Flow<List<SessionExercise>> {
        return combine(
            routineRepository.getRoutineExercises(),
            dataSource.getSessionExercises(sessionIds)
        ) { routineExercises, sessionExercises ->
            sessionExercises.map { sessionExercise ->
                sessionExercise.toSessionExercise(routineExercises.first { it.id == sessionExercise.routineExerciseId })
            }
        }
    }

     override suspend fun updateSessionExercise(sessionExercise: SessionExercise) {
        dataSource.updateSessionExercise(
            sessionExerciseId = sessionExercise.id,
            currentSet = sessionExercise.currentSet,
            endDate = sessionExercise.endDate,
            weight = sessionExercise.weight
        )
    }

     override suspend fun deleteSessionExercises(session: Session) {
        dataSource.deleteSessionExercises(
            sessionId = session.id
        )
    }

    // Sessions

     override suspend fun createSession(
         session: Session,
         generateId: Boolean
    ): Long {
        return dataSource.createSession(
            id = if (generateId) null else session.id,
            startDate = session.startDate,
            endDate = session.endDate,
            routineId = session.routine.id
        )
    }

     override fun getSessions(sessionIds: Collection<Long>?): Flow<List<Session>> {
        return combine(
            dataSource.getSessions(sessionIds),
            routineRepository.getRoutines(),
            getSessionExercises()
        ) { sessions, routines, sessionExercises ->
            sessions.map { session ->
                session.toSession(routine = routines.first { session.routineId == it.id },
                    sessionExercises = sessionExercises.filter { it.sessionId == session.id })
            }
        }
    }

     override fun getSession(sessionId: Long): Flow<Session> {
        return combine(
            dataSource.getSession(sessionId),
            routineRepository.getRoutines(),
            getSessionExercises()
        ) { session, routines, sessionExercises ->
            session.toSession(
                routine = routines
                    .first { routine -> session.routineId == routine.id },
                sessionExercises = sessionExercises
                    .filter { sessionExercise -> sessionExercise.sessionId == session.id }
            )
        }
    }

     override fun getSessionsForRoutine(routineId: Long): Flow<List<Session>> {
        return combine(
            dataSource.getSessionsForRoutine(routineId),
            routineRepository.getRoutine(routineId),
            getSessionExercises()
        ) { sessions, routine, sessionExercises ->
            sessions.map { session ->
                session.toSession(
                    routine = routine,
                    sessionExercises = sessionExercises
                        .filter { sessionExercise -> sessionExercise.sessionId == session.id }
                )
            }
        }
    }

     override fun getLatestSessionForRoutine(routineId: Long): Flow<Session?> {
        return getSessionsForRoutine(routineId).map { sessions -> sessions.maxByOrNull { session -> session.endDate ?: session.startDate } }
    }

     override suspend fun deleteSession(session: Session) {
        dataSource.deleteSession(
            sessionId = session.id
        )
    }

     override suspend fun updateSession(session: Session) {
        dataSource.updateSession(
            sessionId = session.id,
            currentExerciseId = session.currentExercise?.id,
            endDate = session.endDate
        )
    }
}
