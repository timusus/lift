package com.simplecityapps.lift.data.repository

import com.simplecityapps.lift.common.database.AppDataSource
import com.simplecityapps.lift.common.model.Exercise
import com.simplecityapps.lift.common.repository.ExerciseRepository
import com.simplecityapps.lift.data.model.toExercise
import com.simplecityapps.lift.data.model.toFirestoreData
import com.simplecityapps.lift.logging.logcat
import com.simplecityapps.shuttle.common.network.LocalJsonDataSource
import com.simplecityapps.shuttle.common.network.RemoteDataSource
import com.simplecityapps.shuttle.common.network.model.toInstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class DefaultExerciseRepository(
    private val localDataSource: AppDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val localJsonDataSource: LocalJsonDataSource
) : ExerciseRepository {

    override suspend fun upsertExercise(exercise: Exercise) {
        localDataSource.upsertExercise(
            id = exercise.id,
            name = exercise.name,
            oneRepMax = exercise.oneRepMax
        )
    }

    override fun getAllExercises(): Flow<List<Exercise>> {
        return localDataSource.getAllExercises().map { exercises ->
            exercises.map { exercise -> exercise.toExercise() }
        }
    }

    override fun getExercise(id: String): Flow<Exercise?> {
        return localDataSource.getExerciseById(id).map { exercise ->
            exercise?.toExercise()
        }
    }

    override suspend fun importData() {
        localJsonDataSource.getExercises().forEach { exercise ->
            upsertExercise(exercise)
        }
    }

    override suspend fun pullData(userId: String) {
        val lastSyncTime = localDataSource.getExerciseLastSyncTime()
        remoteDataSource.getExerciseEntitiesAfter(
            userId = userId,
            lastSynced = lastSyncTime
        )
            .also { logcat { "Retrieved ${it.size} exercises.. last synced: $lastSyncTime, lastUpdated: ${it.firstOrNull()?.lastUpdated?.toInstant()}" } }
            .forEach { remoteExercise ->
                val localExercise = localDataSource.getExerciseById(remoteExercise.id).firstOrNull()
                if (localExercise == null || localExercise.last_modified < remoteExercise.lastUpdated.toInstant()) {
                    localDataSource.upsertExercise(
                        id = remoteExercise.id,
                        name = remoteExercise.name,
                        oneRepMax = remoteExercise.oneRepMax,
                        lastModified = remoteExercise.lastUpdated.toInstant(),
                        lastSynced = remoteExercise.lastUpdated.toInstant()
                    )
                }
            }
    }

    override suspend fun pushData(userId: String) {
        val exercisesToSync = localDataSource.getExercisesToSync().first()
        if (exercisesToSync.isNotEmpty()) {
            logcat { "Syncing ${exercisesToSync.size} exercises" }
            remoteDataSource.pushExerciseEntities(
                userId = userId,
                exerciseRequests = exercisesToSync.map { exerciseEntity -> toFirestoreData(exerciseEntity) }
            )
            localDataSource.updateExerciseLastSynced(
                ids = exercisesToSync.map { exerciseEntity -> exerciseEntity.id },
                time = Clock.System.now()
            )
        }
    }
}
