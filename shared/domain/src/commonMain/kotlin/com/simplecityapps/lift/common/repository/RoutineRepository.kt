package com.simplecityapps.lift.common.repository

import com.simplecityapps.lift.common.model.Routine
import com.simplecityapps.lift.common.model.RoutineExercise
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    suspend fun createRoutineExercise(
        routineExercise: RoutineExercise,
        generateId: Boolean
    )

    fun getRoutineExercises(routineIds: Collection<Long>? = null): Flow<List<RoutineExercise>>

    suspend fun updateRoutineExercisePercentOneRepMax(id: Long, percentOneRepMax: Float?)

    suspend fun updateRoutineExerciseWeight(id: Long, weight: Float?)

    suspend fun updateRoutineExerciseOneRepMax(id: Long, oneRepMax: Float?)

    suspend fun createRoutine(
        routine: Routine,
        generateId: Boolean
    )

    fun getRoutines(ids: Collection<Long>? = null): Flow<List<Routine>>
    fun getRoutine(id: Long): Flow<Routine>
}