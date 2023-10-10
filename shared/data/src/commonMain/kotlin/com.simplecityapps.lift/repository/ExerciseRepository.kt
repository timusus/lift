package com.simplecityapps.lift.repository

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.simplecityapps.lift.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    @NativeCoroutines
    suspend fun createExercise(
        exercise: Exercise,
        generateId: Boolean = true
    )

    @NativeCoroutines
    fun getAllExercises(): Flow<List<Exercise>>
}
