package com.simplecityapps.lift.common.repository

import com.simplecityapps.lift.common.model.Routine
import com.simplecityapps.lift.common.model.RoutineExercise
import kotlinx.coroutines.flow.Flow

interface RoutineRepository: SyncRepository {
    suspend fun upsertRoutineExercise(routineExercise: RoutineExercise)

    fun getRoutineExercises(routineIds: Collection<String>? = null): Flow<List<RoutineExercise>>

    suspend fun updateRoutineExercisePercentOneRepMax(id: String, percentOneRepMax: Float?)

    suspend fun updateRoutineExerciseWeight(id: String, weight: Float?)

    suspend fun updateRoutineExerciseOneRepMax(id: String, oneRepMax: Float?)

    suspend fun upsertRoutine(routine: Routine)

    fun getRoutines(ids: Collection<String>? = null): Flow<List<Routine>>
    fun getRoutine(id: String): Flow<Routine?>
}