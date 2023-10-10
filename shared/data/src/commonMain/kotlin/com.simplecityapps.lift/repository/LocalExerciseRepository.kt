package com.simplecityapps.lift.repository

import com.simplecityapps.lift.model.Exercise
import com.simplecityapps.lift.model.toExerciseEntity
import com.simplecityapps.shuttle.common.database.AppDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalExerciseRepository(
    private val dataSource: AppDataSource
) : ExerciseRepository {

    override suspend fun createExercise(
        exercise: Exercise,
        generateId: Boolean
    ) {
        dataSource.createExercise(
            exercise = exercise.toExerciseEntity(),
            generateId = generateId
        )
    }

    override fun getAllExercises(): Flow<List<Exercise>> {
        return dataSource.getAllExercises().map { exercises ->
            exercises.map { exercise -> exercise.toExerciseEntity() }
        }
    }
}
