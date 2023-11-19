package com.simplecityapps.lift.common.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.simplecityapps.lift.common.utils.UuidGenerator
import com.simplecityapps.lift.database.LiftDatabase
import comsimplecityappslift.common.database.ExerciseEntity
import comsimplecityappslift.common.database.RoutineEntity
import comsimplecityappslift.common.database.RoutineExerciseEntity
import comsimplecityappslift.common.database.RunSessionLocationEntity
import comsimplecityappslift.common.database.SessionEntity
import comsimplecityappslift.common.database.SessionExerciseEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class AppDataSource(
    private val database: LiftDatabase,
    private val dispatcher: CoroutineDispatcher
) {

    // Exercises

    suspend fun upsertExercise(
        id: String?,
        name: String,
        oneRepMax: Float?,
        lastModified: Instant = Clock.System.now(),
        lastSynced: Instant? = null
    ) {
        return withContext(dispatcher) {
            database.databaseQueries.upsertExercise(
                id = id ?: UuidGenerator.generateUuid(),
                name = name,
                one_rep_max = oneRepMax,
                last_modified = lastModified,
                last_synced = lastSynced
            )
        }
    }

    fun getAllExercises(): Flow<List<ExerciseEntity>> {
        return database.databaseQueries.selectAllExercises()
            .asFlow().mapToList(dispatcher)
    }

    fun getExercisesToSync(): Flow<List<ExerciseEntity>> {
        return database.databaseQueries.selectExercisesToSync()
            .asFlow().mapToList(dispatcher)
    }

    fun getExerciseById(id: String): Flow<ExerciseEntity?> {
        return database.databaseQueries.selectExercise(id)
            .asFlow()
            .mapToOneOrNull(dispatcher)
    }

    suspend fun getExerciseLastSyncTime(): Instant? {
        return withContext(dispatcher) {
            database.databaseQueries.selectLastSyncedExerciseInstant().executeAsOneOrNull()?.last_synced
        }
    }

    suspend fun updateExerciseLastSynced(ids: List<String>, time: Instant) {
        return withContext(dispatcher) {
            database.databaseQueries.updateExerciseLastSynced(
                last_synced = time,
                id = ids
            )
        }
    }

    // Routine Exercises

    suspend fun upsertRoutineExercise(
        id: String?,
        sortOrder: Int,
        sets: Int,
        reps: Int,
        percentOneRepMax: Float?,
        weight: Float?,
        routineId: String,
        exerciseId: String,
        lastModified: Instant = Clock.System.now(),
        lastSynced: Instant? = null
    ) {
        return withContext(dispatcher) {
            database.databaseQueries.upsertRoutineExercise(
                id = id ?: UuidGenerator.generateUuid(),
                sort_order = sortOrder,
                sets = sets,
                reps = reps,
                percent_one_rep_max = percentOneRepMax,
                weight = weight,
                routineId = routineId,
                exerciseId = exerciseId,
                last_modified = lastModified,
                last_synced = lastSynced
            )
        }
    }

    fun getRoutineExercises(routineIds: Collection<String>? = null): Flow<List<RoutineExerciseEntity>> {
        return (routineIds?.let {
            database.databaseQueries.selectRoutineExercises(routineIds)
        } ?: database.databaseQueries.selectAllRoutineExercises())
            .asFlow().mapToList(dispatcher)
    }

    fun getRoutineExercise(id: String): Flow<RoutineExerciseEntity?> {
        return database.databaseQueries.selectRoutineExercise(id)
            .asFlow()
            .mapToOneOrNull(dispatcher)
    }

    fun getRoutineExercisesToSync(): Flow<List<RoutineExerciseEntity>> {
        return database.databaseQueries.selectRoutineExercisesToSync()
            .asFlow().mapToList(dispatcher)
    }

    suspend fun getRoutineExerciseLastSyncTime(): Instant? {
        return withContext(dispatcher) {
            database.databaseQueries.selectLastSyncedRoutineExerciseInstant().executeAsOneOrNull()?.last_synced
        }
    }

    suspend fun updateRoutineExerciseLastSynced(ids: List<String>, time: Instant) {
        return withContext(dispatcher) {
            database.databaseQueries.updateRoutineExerciseLastSynced(
                last_synced = time,
                id = ids
            )
        }
    }

    suspend fun updateRoutineExercisePercentOneRepMax(id: String, percentOneRepMax: Float?) {
        withContext(dispatcher) {
            database.databaseQueries.updateRoutineExercisePercentOneRepMax(percentOneRepMax, id)
        }
    }

    suspend fun updateRoutineExerciseWeight(id: String, weight: Float?) {
        withContext(dispatcher) {
            database.databaseQueries.updateRoutineExerciseWeight(weight, id)
        }
    }

    suspend fun updateExerciseOneRepMax(id: String, oneRepMax: Float?) {
        withContext(dispatcher) {
            database.databaseQueries.updateExerciseOneRepMax(oneRepMax, id)
        }
    }

    suspend fun updateRoutineLastSynced(ids: List<String>, time: Instant) {
        withContext(dispatcher) {
            database.databaseQueries.updateRoutineLastSynced(
                id = ids,
                last_synced = time
            )
        }
    }

    // Routines

    suspend fun upsertRoutine(
        id: String?,
        sortOrder: Int,
        name: String,
        lastModified: Instant = Clock.System.now(),
        lastSynced: Instant? = null
    ) {
        return withContext(dispatcher) {
            database.databaseQueries.upsertRoutine(
                id = id ?: UuidGenerator.generateUuid(),
                sort_order = sortOrder,
                name = name,
                last_modified = lastModified,
                last_synced = lastSynced
            )
        }
    }

    fun getRoutines(ids: Collection<String>? = null): Flow<List<RoutineEntity>> {
        return (ids?.let {
            database.databaseQueries.selectRoutines(ids)
        } ?: database.databaseQueries.selectAllRoutines())
            .asFlow()
            .mapToList(dispatcher)
    }

    fun getRoutinesToSync(): Flow<List<RoutineEntity>> {
        return database.databaseQueries.selectRoutinesToSync()
            .asFlow().mapToList(dispatcher)
    }

    fun getRoutine(id: String): Flow<RoutineEntity?> {
        return database.databaseQueries.selectRoutine(id)
            .asFlow()
            .mapToOneOrNull(dispatcher)
    }

    suspend fun getRoutineLastSyncTime(): Instant? {
        return withContext(dispatcher) {
            database.databaseQueries.selectLastSyncedRoutineInstant().executeAsOneOrNull()?.last_synced
        }
    }

    // Session Exercises

    suspend fun upsertSessionExercise(
        id: String?,
        sets: Int?,
        reps: Int?,
        weight: Float?,
        sessionId: String,
        routineExerciseId: String,
        lastModified: Instant = Clock.System.now(),
        lastSynced: Instant? = null
    ) {
        return withContext(dispatcher) {
            database.databaseQueries.upsertSessionExercise(
                id = id ?: UuidGenerator.generateUuid(),
                sets = sets,
                reps = reps,
                weight = weight,
                sessionId = sessionId,
                routineExerciseId = routineExerciseId,
                last_modified = lastModified,
                last_synced = lastSynced
            )
        }
    }

    fun getSessionExercises(sessionIds: Collection<String>?): Flow<List<SessionExerciseEntity>> {
        return (sessionIds?.let {
            database.databaseQueries.selectSessionExercises(sessionIds)
        } ?: database.databaseQueries.selectAllSessionExercises())
            .asFlow().mapToList(dispatcher)
    }

    fun getSessionExercise(sessionId: String): Flow<SessionExerciseEntity?> {
        return database.databaseQueries.selectSessionExercise(sessionId)
            .asFlow().mapToOneOrNull(dispatcher)
    }

    fun getSessionExercisesToSync(): Flow<List<SessionExerciseEntity>> {
        return database.databaseQueries.selectSessionExercisesToSync()
            .asFlow().mapToList(dispatcher)
    }

    suspend fun getSessionExerciseLastSyncTime(): Instant? {
        return withContext(dispatcher) {
            database.databaseQueries.selectLastSyncedSessionExerciseInstant().executeAsOneOrNull()?.last_synced
        }
    }

    suspend fun updateSessionExercise(sessionExerciseId: String, currentSet: Int, endDate: Instant?, weight: Float?) {
        withContext(dispatcher) {
            database.databaseQueries.updateSessionExercise(
                sessionExerciseId = sessionExerciseId,
                currentSet = currentSet,
                endDate = endDate,
                weight = weight
            )
        }
    }

    suspend fun deleteSessionExercises(sessionId: String) {
        withContext(dispatcher) {
            database.databaseQueries.deleteExercisesForSession(sessionId)
        }
    }

    // Sessions

    suspend fun upsertSession(
        id: String?,
        startDate: Instant,
        endDate: Instant?,
        routineId: String,
        lastModified: Instant = Clock.System.now(),
        lastSynced: Instant? = null
    ) {
        return withContext(dispatcher) {
            database.databaseQueries.upsertSession(
                id = id ?: UuidGenerator.generateUuid(),
                startDate = startDate,
                endDate = endDate,
                routineId = routineId,
                last_modified = lastModified,
                last_synced = lastSynced
            )
        }
    }

    fun getSessions(sessionIds: Collection<String>?): Flow<List<SessionEntity>> {
        return (
                if (sessionIds == null) {
                    database.databaseQueries.selectAllSessions()
                } else {
                    database.databaseQueries.selectSessions(sessionIds)
                }
                )
            .asFlow().mapToList(dispatcher)
    }

    fun getSessionsToSync(): Flow<List<SessionEntity>> {
        return database.databaseQueries.selectSessionsToSync()
            .asFlow().mapToList(dispatcher)
    }

    fun getSession(sessionId: String): Flow<SessionEntity?> {
        return database.databaseQueries.selectSession(sessionId)
            .asFlow().mapToOneOrNull(dispatcher)
    }

    fun getSessionsForRoutine(routineId: String): Flow<List<SessionEntity>> {
        return database.databaseQueries.selectSessionsForRoutine(routineId)
            .asFlow().mapToList(dispatcher)
    }

    suspend fun getSessionLastSyncTime(): Instant? {
        return withContext(dispatcher) {
            database.databaseQueries.selectLastSyncedSessionInstant().executeAsOneOrNull()?.last_synced
        }
    }

    suspend fun updateSession(sessionId: String, currentExerciseId: String?, endDate: Instant?) {
        withContext(dispatcher) {
            database.databaseQueries.updateSession(
                sessionId = sessionId,
                currentExerciseId = currentExerciseId,
                endDate = endDate
            )
        }
    }

    suspend fun updateSessionLastSynced(ids: List<String>, time: Instant) {
        withContext(dispatcher) {
            database.databaseQueries.updateSessionLastSynced(
                id = ids,
                last_synced = time
            )
        }
    }

    suspend fun updateSessionExerciseLastSynced(ids: List<String>, time: Instant) {
        withContext(dispatcher) {
            database.databaseQueries.updateSessionExerciseLastSynced(
                id = ids,
                last_synced = time
            )
        }
    }

    suspend fun deleteSession(sessionId: String) {
        withContext(dispatcher) {
            database.databaseQueries.deleteSession(sessionId)
        }
    }

    // Runs

    fun startRunningSession(): String {
        val id = UuidGenerator.generateUuid()
        val startDate = Clock.System.now()
        return database.transactionWithResult {
            database.databaseQueries.insertRunSession(
                id = id,
                startDate = startDate,
                endDate = null,
                last_modified = Clock.System.now()
            )
            id
        }
    }

    fun stopRunningSession(sessionId: String) {
        val endDate = Clock.System.now()
        database.databaseQueries.updateEndDate(
            endDate = endDate,
            id = sessionId
        )
    }

    fun addLocationToSession(sessionId: String, latitude: Double, longitude: Double) {
        database.databaseQueries.insertLocation(
            id = UuidGenerator.generateUuid(),
            sessionId = sessionId,
            latitude = latitude,
            longitude = longitude,
            timestamp = Clock.System.now(),
            last_modified = Clock.System.now()
        )
    }

    fun getSessionLocations(sessionId: String): Flow<List<RunSessionLocationEntity>> {
        return database.databaseQueries.getLocationsForRunSession(sessionId).asFlow().mapToList(dispatcher)
    }
}