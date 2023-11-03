package com.simplecityapps.lift.common.repository

import com.simplecityapps.lift.common.model.Session
import com.simplecityapps.lift.common.model.SessionExercise
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    suspend fun createSessionExercise(
        sessionExercise: SessionExercise,
        generateId: Boolean = true
    ): Long

    fun getSessionExercises(sessionIds: Collection<Long>? = null): Flow<List<SessionExercise>>

    suspend fun updateSessionExercise(sessionExercise: SessionExercise)

    suspend fun deleteSessionExercises(session: Session)

    suspend fun createSession(
        session: Session,
        generateId: Boolean
    ): Long

    fun getSessions(sessionIds: Collection<Long>? = null): Flow<List<Session>>
    fun getSession(sessionId: Long): Flow<Session>
    fun getSessionsForRoutine(routineId: Long): Flow<List<Session>>
    fun getLatestSessionForRoutine(routineId: Long): Flow<Session?>

    suspend fun deleteSession(session: Session)

    suspend fun updateSession(session: Session)
}