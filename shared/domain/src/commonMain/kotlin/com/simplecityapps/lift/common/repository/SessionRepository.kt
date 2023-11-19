package com.simplecityapps.lift.common.repository

import com.simplecityapps.lift.common.model.Session
import com.simplecityapps.lift.common.model.SessionExercise
import kotlinx.coroutines.flow.Flow

interface SessionRepository: SyncRepository {
    suspend fun upsertSessionExercise(sessionExercise: SessionExercise)

    fun getSessionExercises(sessionIds: Collection<String>? = null): Flow<List<SessionExercise>>

    suspend fun updateSessionExercise(sessionExercise: SessionExercise)

    suspend fun deleteSessionExercises(session: Session)

    suspend fun upsertSession(session: Session)

    fun getSessions(sessionIds: Collection<String>? = null): Flow<List<Session>>
    fun getSession(sessionId: String): Flow<Session?>
    fun getSessionsForRoutine(routineId: String): Flow<List<Session>>
    fun getLatestSessionForRoutine(routineId: String): Flow<Session?>

    suspend fun deleteSession(session: Session)

    suspend fun updateSession(session: Session)
}