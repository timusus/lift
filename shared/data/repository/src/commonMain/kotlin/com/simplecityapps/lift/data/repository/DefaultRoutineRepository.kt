package com.simplecityapps.lift.data.repository

import com.simplecityapps.lift.common.database.AppDataSource
import com.simplecityapps.lift.common.model.Routine
import com.simplecityapps.lift.common.model.RoutineExercise
import com.simplecityapps.lift.common.repository.ExerciseRepository
import com.simplecityapps.lift.common.repository.RoutineRepository
import com.simplecityapps.lift.data.model.toFirestoreData
import com.simplecityapps.lift.data.model.toRoutine
import com.simplecityapps.lift.data.model.toRoutineExercise
import com.simplecityapps.lift.logging.logcat
import com.simplecityapps.shuttle.common.network.LocalJsonDataSource
import com.simplecityapps.shuttle.common.network.RemoteDataSource
import com.simplecityapps.shuttle.common.network.model.toInstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.Clock

class DefaultRoutineRepository(
    private val localDataSource: AppDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val exerciseRepository: ExerciseRepository,
    private val localJsonDataSource: LocalJsonDataSource,
) : RoutineRepository {

    // Routine Exercises

    override suspend fun upsertRoutineExercise(routineExercise: RoutineExercise) {
        localDataSource.upsertRoutineExercise(
            id = routineExercise.id,
            sortOrder = routineExercise.order,
            sets = routineExercise.sets,
            reps = routineExercise.reps,
            percentOneRepMax = routineExercise.percentOneRepMax,
            weight = routineExercise.weight,
            routineId = routineExercise.routineId,
            exerciseId = routineExercise.exercise.id
        )
    }

    override fun getRoutineExercises(routineIds: Collection<String>?): Flow<List<RoutineExercise>> {
        return combine(
            localDataSource.getRoutineExercises(routineIds),
            exerciseRepository.getAllExercises(),
        ) { routineExercises, exercises ->
            routineExercises.mapNotNull { routineExercise ->
                exercises.firstOrNull { exercise -> exercise.id == routineExercise.exerciseId }
                    ?.let { exercise ->
                        routineExercise.toRoutineExercise(exercise)
                    }
            }
        }
    }

    override suspend fun updateRoutineExercisePercentOneRepMax(id: String, percentOneRepMax: Float?) {
        localDataSource.updateRoutineExercisePercentOneRepMax(
            id = id, percentOneRepMax = percentOneRepMax
        )
    }

    override suspend fun updateRoutineExerciseWeight(id: String, weight: Float?) {
        localDataSource.updateRoutineExerciseWeight(
            id = id, weight = weight
        )
    }

    override suspend fun updateRoutineExerciseOneRepMax(id: String, oneRepMax: Float?) {
        localDataSource.updateExerciseOneRepMax(
            id = id, oneRepMax = oneRepMax
        )
    }

    // Routines

    override suspend fun upsertRoutine(routine: Routine) {
        localDataSource.upsertRoutine(
            id = routine.id,
            sortOrder = routine.order,
            name = routine.name
        )
    }

    override fun getRoutines(ids: Collection<String>?): Flow<List<Routine>> {
        return combine(
            localDataSource.getRoutines(ids),
            getRoutineExercises()
        ) { routines, routineExercises ->
            routines.map { routine ->
                routine.toRoutine(routineExercises.filter { it.routineId == routine.id })
            }
        }
    }

    override fun getRoutine(id: String): Flow<Routine?> {
        return combine(
            localDataSource.getRoutine(id),
            getRoutineExercises()
        ) { routine, routineExercises ->
            routine?.toRoutine(routineExercises.filter { it.routineId == routine.id })
        }
    }

    override suspend fun importData() {
        val exercises = localJsonDataSource.getExercises()
        val routineExercises = localJsonDataSource.getRoutineExercises(exercises)
        val routines = localJsonDataSource.getRoutines(routineExercises)
        routines.forEach {
            upsertRoutine(it)
        }
        routineExercises.forEach {
            upsertRoutineExercise(it)
        }
    }

    override suspend fun pullData(userId: String) {
        val routineLastSyncTime = localDataSource.getRoutineLastSyncTime()

        // Routines
        remoteDataSource.getRoutineEntitiesAfter(
            userId = userId,
            lastSynced = routineLastSyncTime
        )
            .also { logcat { "Retrieved ${it.size} routines" } }
            .forEach { remoteRoutine ->
                val localRoutine = localDataSource.getRoutine(remoteRoutine.id).firstOrNull()
                if (localRoutine == null || localRoutine.last_modified < remoteRoutine.lastUpdated.toInstant()) {
                    localDataSource.upsertRoutine(
                        id = remoteRoutine.id,
                        sortOrder = remoteRoutine.sortOrder,
                        name = remoteRoutine.name,
                        lastModified = remoteRoutine.lastUpdated.toInstant(),
                        lastSynced = remoteRoutine.lastUpdated.toInstant()
                    )
                }
            }

        val routineExerciseLastSyncTime = localDataSource.getRoutineExerciseLastSyncTime()

        // Routine Exercises
        remoteDataSource.getRoutineExerciseEntitiesAfter(
            userId = userId,
            lastSynced = routineExerciseLastSyncTime
        )
            .also { logcat { "Retrieved ${it.size} routine exercises" } }
            .forEach { remoteRoutineExercise ->
                val localRoutineExercise = localDataSource.getRoutineExercise(remoteRoutineExercise.id).firstOrNull()
                if (localRoutineExercise == null || localRoutineExercise.last_modified < remoteRoutineExercise.lastUpdated.toInstant()) {
                    localDataSource.upsertRoutineExercise(
                        id = remoteRoutineExercise.id,
                        sortOrder = remoteRoutineExercise.sortOrder,
                        sets = remoteRoutineExercise.sets,
                        reps = remoteRoutineExercise.reps,
                        percentOneRepMax = remoteRoutineExercise.percentOneRepMax,
                        weight = remoteRoutineExercise.weight,
                        routineId = remoteRoutineExercise.routineId,
                        exerciseId = remoteRoutineExercise.exerciseId,
                        lastModified = remoteRoutineExercise.lastUpdated.toInstant(),
                        lastSynced = remoteRoutineExercise.lastUpdated.toInstant()
                    )
                }
            }
    }

    override suspend fun pushData(userId: String) {
        // Routines

        val routinesToSync = localDataSource.getRoutinesToSync().first()
        if (routinesToSync.isNotEmpty()) {
            logcat { "Syncing ${routinesToSync.size} routines" }
            remoteDataSource.pushRoutineEntities(
                userId = userId,
                routineRequests = routinesToSync.map { routineEntity -> toFirestoreData(routineEntity) }
            )

            localDataSource.updateRoutineLastSynced(
                ids = routinesToSync.map { routineEntity -> routineEntity.id },
                time = Clock.System.now()
            )
        }

        // Routine Exercises

        val routineExercisesToSync = localDataSource.getRoutineExercisesToSync().first()
        if (routineExercisesToSync.isNotEmpty()) {
            logcat { "Syncing ${routineExercisesToSync.size} routine exercises" }
            remoteDataSource.pushRoutineExerciseEntities(
                userId = userId,
                routineExerciseRequests = routineExercisesToSync.map { routineExerciseEntity -> toFirestoreData(routineExerciseEntity) }
            )

            localDataSource.updateRoutineExerciseLastSynced(
                ids = routineExercisesToSync.map { routineExerciseEntity -> routineExerciseEntity.id },
                time = Clock.System.now()
            )
        }
    }
}
