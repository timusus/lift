package com.simplecityapps.lift.repository

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.simplecityapps.lift.model.Session
import com.simplecityapps.lift.model.SessionExercise
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    // Session Exercises

    @NativeCoroutines
    suspend fun createSessionExercise(
        sessionExercise: SessionExercise,
        generateId: Boolean = true
    ): Long

    @NativeCoroutines
    fun getSessionExercises(sessionIds: Collection<Long>? = null): Flow<List<SessionExercise>>

    @NativeCoroutines
    suspend fun updateSessionExercise(sessionExercise: SessionExercise)

    @NativeCoroutines
    suspend fun deleteSessionExercises(session: Session)

    // Sessions

    @NativeCoroutines
    suspend fun createSession(
        session: Session,
        generateId: Boolean = true
    ): Long

    @NativeCoroutines
    fun getSessionsForRoutine(routineId: Long): Flow<List<Session>>

    @NativeCoroutines
    fun getLatestSessionForRoutine(routineId: Long): Flow<Session?>

    @NativeCoroutines
    fun getSessions(sessionIds: Collection<Long>? = null): Flow<List<Session>>

    @NativeCoroutines
    fun getSession(sessionId: Long): Flow<Session>

    @NativeCoroutines
    suspend fun updateSession(session: Session)

    @NativeCoroutines
    suspend fun deleteSession(session: Session)
}