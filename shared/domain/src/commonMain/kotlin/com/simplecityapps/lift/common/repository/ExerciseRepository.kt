package com.simplecityapps.lift.common.repository

import com.simplecityapps.lift.common.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository : SyncRepository {
    suspend fun upsertExercise(exercise: Exercise)
    fun getAllExercises(): Flow<List<Exercise>>
    fun getExercise(id: String): Flow<Exercise?>
}