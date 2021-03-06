package com.acompany.lift.data

import com.acompany.lift.data.model.Exercise
import com.acompany.lift.data.model.Routine
import com.acompany.lift.data.model.RoutineExercise
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getAllRoutines(): Flow<List<Routine>>
    fun getRoutines(ids: Collection<Long>): Flow<List<Routine>>
    fun getRoutineExercises(): Flow<List<RoutineExercise>>
    fun getRoutineExercises(routineIds: Collection<Long>): Flow<List<RoutineExercise>>
    fun getAllExercises(): Flow<List<Exercise>>
    suspend fun updateRoutineExercisePercentOneRepMax(id: Long, percentOneRepMax: Float?)
    suspend fun updateRoutineExerciseWeight(id: Long, weight: Float?)
    suspend fun updateExerciseOneRepMax(id: Long, oneRepMax: Float?)
}