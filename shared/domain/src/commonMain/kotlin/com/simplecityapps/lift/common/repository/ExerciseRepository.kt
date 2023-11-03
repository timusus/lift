package com.simplecityapps.lift.common.repository

import com.simplecityapps.lift.common.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository: SyncRepository {
    suspend fun createExercise(
        exercise: Exercise,
        generateId: Boolean
    )

    fun getAllExercises(): Flow<List<Exercise>>
}