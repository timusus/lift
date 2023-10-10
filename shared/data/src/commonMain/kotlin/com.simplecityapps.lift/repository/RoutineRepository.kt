package com.simplecityapps.lift.repository

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.simplecityapps.lift.model.Routine
import com.simplecityapps.lift.model.RoutineExercise
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    // Routine Exercises

    @NativeCoroutines
    suspend fun createRoutineExercise(
        routineExercise: RoutineExercise,
        generateId: Boolean = true
    )

    @NativeCoroutines
    fun getRoutineExercises(routineIds: Collection<Long>? = null): Flow<List<RoutineExercise>>

    @NativeCoroutines
    suspend fun updateRoutineExercisePercentOneRepMax(id: Long, percentOneRepMax: Float?)

    @NativeCoroutines
    suspend fun updateRoutineExerciseWeight(id: Long, weight: Float?)

    @NativeCoroutines
    suspend fun updateRoutineExerciseOneRepMax(id: Long, oneRepMax: Float?)

    // Routines

    @NativeCoroutines
    suspend fun createRoutine(
        routine: Routine,
        generateId: Boolean = true
    )

    @NativeCoroutines
    fun getRoutines(ids: Collection<Long>? = null): Flow<List<Routine>>

    @NativeCoroutines
    fun getRoutine(id: Long): Flow<Routine>
}
