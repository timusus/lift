package com.simplecityapps.lift.repository

import com.simplecityapps.lift.common.database.AppDataSource
import com.simplecityapps.lift.common.model.Exercise
import com.simplecityapps.lift.common.repository.ExerciseRepository
import com.simplecityapps.lift.model.toExerciseEntity
import com.simplecityapps.lift.model.toFirestoreData
import com.simplecityapps.shuttle.common.network.RemoteDataSource
import comsimplecityappslift.common.database.ExerciseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DefaultExerciseRepository(
    private val localDataSource: AppDataSource,
    private val remoteDataSource: RemoteDataSource,
) : ExerciseRepository {

    override suspend fun createExercise(
        exercise: Exercise,
        generateId: Boolean
    ) {
        localDataSource.createExercise(
            id = if (generateId) null else exercise.id,
            name = exercise.name,
            oneRepMax = exercise.oneRepMax
        )
    }

    override fun getAllExercises(): Flow<List<Exercise>> {
        return localDataSource.getAllExercises().map { exercises ->
            exercises.map { exercise -> exercise.toExerciseEntity() }
        }
    }

    override suspend fun syncData(userId: String) {
        val unsyncedExercises = localDataSource.getAllExercises().first().filter { !it.synced }
        remoteDataSource.pushExerciseEntity(userId = userId, data = unsyncedExercises.map(ExerciseEntity::toFirestoreData))
    }
}
