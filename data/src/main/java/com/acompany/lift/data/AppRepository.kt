package com.acompany.lift.data

import com.acompany.lift.data.model.*
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getRoutines(ids: Collection<Long>? = null): Flow<List<Routine>>
    fun getRoutineExercises(routineIds: Collection<Long>? = null): Flow<List<RoutineExercise>>
    fun getAllExercises(): Flow<List<Exercise>>
    fun getSessions(sessionIds: Collection<Long>? = null): Flow<List<Session>>
    fun getSessionExercises(): Flow<List<SessionExercise>>
    suspend fun updateRoutineExercisePercentOneRepMax(id: Long, percentOneRepMax: Float?)
    suspend fun updateRoutineExerciseWeight(id: Long, weight: Float?)
    suspend fun updateExerciseOneRepMax(id: Long, oneRepMax: Float?)
    suspend fun createSession(session: Session)
    suspend fun deleteSession(session: Session)
    suspend fun deleteSessionExercises(session: Session)
}