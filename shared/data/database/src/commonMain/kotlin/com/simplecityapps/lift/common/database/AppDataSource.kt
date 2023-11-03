package com.simplecityapps.lift.common.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.simplecityapps.lift.database.LiftDatabase
import comsimplecityappslift.common.database.ExerciseEntity
import comsimplecityappslift.common.database.RoutineEntity
import comsimplecityappslift.common.database.RoutineExerciseEntity
import comsimplecityappslift.common.database.RunSessionLocationEntity
import comsimplecityappslift.common.database.SessionEntity
import comsimplecityappslift.common.database.SessionExerciseEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class AppDataSource(
    private val database: LiftDatabase
) {

    // Exercises

    suspend fun createExercise(
        id: Long?, name: String, oneRepMax: Float?
    ): Long {
        return withContext(Dispatchers.Default) {
            database.transactionWithResult {
                database.databaseQueries.createExercise(
                    id = id,
                    name = name,
                    one_rep_max = oneRepMax
                )
                database.databaseQueries.lastInsertId().executeAsOne()
            }
        }
    }

    fun getAllExercises(): Flow<List<ExerciseEntity>> {
        return database.databaseQueries.selectAllExercises()
            .asFlow().mapToList(Dispatchers.Default)
    }

    // Routine Exercises

    suspend fun createRoutineExercise(
        id: Long?,
        sortOrder: Int,
        sets: Int,
        reps: Int,
        percentOneRepMax: Float?,
        weight: Float?,
        routineId: Long,
        exerciseId: Long
    ): Long {
        return withContext(Dispatchers.Default) {
            database.transactionWithResult {
                database.databaseQueries.createRoutineExercise(
                    id = id,
                    sort_order = sortOrder,
                    sets = sets,
                    reps = reps,
                    percent_one_rep_max = percentOneRepMax,
                    weight = weight,
                    routineId = routineId,
                    exerciseId = exerciseId
                )
                database.databaseQueries.lastInsertId().executeAsOne()
            }
        }
    }

    fun getRoutineExercises(routineIds: Collection<Long>? = null): Flow<List<RoutineExerciseEntity>> {
        return (routineIds?.let {
            database.databaseQueries.selectRoutineExercises(routineIds)
        } ?: database.databaseQueries.selectAllRoutineExercises())
            .asFlow().mapToList(Dispatchers.Default)
    }

    suspend fun updateRoutineExercisePercentOneRepMax(id: Long, percentOneRepMax: Float?) {
        withContext(Dispatchers.Default) {
            database.databaseQueries.updateRoutineExercisePercentOneRepMax(percentOneRepMax, id)
        }
    }

    suspend fun updateRoutineExerciseWeight(id: Long, weight: Float?) {
        withContext(Dispatchers.Default) {
            database.databaseQueries.updateRoutineExerciseWeight(weight, id)
        }
    }

    suspend fun updateExerciseOneRepMax(id: Long, oneRepMax: Float?) {
        withContext(Dispatchers.Default) {
            database.databaseQueries.updateExerciseOneRepMax(oneRepMax, id)
        }
    }

    // Routines

    suspend fun createRoutine(
        id: Long?,
        sortOrder: Int,
        name: String
    ): Long {
        return withContext(Dispatchers.Default) {
            database.transactionWithResult {
                database.databaseQueries.createRoutine(
                    id = id,
                    sort_order = sortOrder,
                    name = name
                )
                database.databaseQueries.lastInsertId().executeAsOne()
            }
        }
    }

    fun getRoutines(ids: Collection<Long>? = null): Flow<List<RoutineEntity>> {
        return (ids?.let {
            database.databaseQueries.selectRoutines(ids)
        } ?: database.databaseQueries.selectAllRoutines())
            .asFlow()
            .mapToList(Dispatchers.Default)
    }

    fun getRoutine(id: Long): Flow<RoutineEntity> {
        return database.databaseQueries.selectRoutine(id)
            .asFlow()
            .mapToOne(Dispatchers.Default)
    }

    // Session Exercises

    suspend fun createSessionExercise(
        id: Long?,
        sets: Int?,
        reps: Int?,
        weight: Float?,
        sessionId: Long,
        routineExerciseId: Long,
    ): Long {
        return withContext(Dispatchers.Default) {
            database.transactionWithResult {
                database.databaseQueries.createSessionExercise(
                    id = id,
                    sets = sets,
                    reps = reps,
                    weight = weight,
                    sessionId = sessionId,
                    routineExerciseId = routineExerciseId
                )
                database.databaseQueries.lastInsertId().executeAsOne()
            }
        }
    }

    fun getSessionExercises(sessionIds: Collection<Long>?): Flow<List<SessionExerciseEntity>> {
        return (sessionIds?.let {
            database.databaseQueries.selectSessionExercises(sessionIds)
        } ?: database.databaseQueries.selectAllSessionExercises())
            .asFlow().mapToList(Dispatchers.Default)
    }

    suspend fun updateSessionExercise(sessionExerciseId: Long, currentSet: Int, endDate: Instant?, weight: Float?) {
        withContext(Dispatchers.Default) {
            database.databaseQueries.updateSessionExercise(
                sessionExerciseId = sessionExerciseId,
                currentSet = currentSet,
                endDate = endDate,
                weight = weight
            )
        }
    }

    suspend fun deleteSessionExercises(sessionId: Long) {
        withContext(Dispatchers.Default) {
            database.databaseQueries.deleteExercisesForSession(sessionId)
        }
    }

    // Sessions

    suspend fun createSession(
        id: Long?,
        startDate: Instant,
        endDate: Instant?,
        routineId: Long,
    ): Long {
        return withContext(Dispatchers.Default) {
            database.transactionWithResult {
                database.databaseQueries.createSession(
                    id = id,
                    startDate = startDate,
                    endDate = endDate,
                    routineId = routineId
                )
                database.databaseQueries.lastInsertId().executeAsOne()
            }
        }
    }

    fun getSessions(sessionIds: Collection<Long>?): Flow<List<SessionEntity>> {
        return (if (sessionIds == null) {
            database.databaseQueries.selectAllSessions()
        } else {
            database.databaseQueries.selectSessions(sessionIds)
        })
            .asFlow().mapToList(Dispatchers.Default)
    }

    fun getSession(sessionId: Long): Flow<SessionEntity> {
        return database.databaseQueries.selectSession(sessionId)
            .asFlow().mapToOne(Dispatchers.Default)
    }

    fun getSessionsForRoutine(routineId: Long): Flow<List<SessionEntity>> {
        return database.databaseQueries.selectSessionsForRoutine(routineId)
            .asFlow().mapToList(Dispatchers.Default)
    }

    suspend fun updateSession(sessionId: Long, currentExerciseId: Long?, endDate: Instant?) {
        withContext(Dispatchers.Default) {
            database.databaseQueries.updateSession(
                sessionId = sessionId,
                currentExerciseId = currentExerciseId,
                endDate = endDate
            )
        }
    }

    suspend fun deleteSession(sessionId: Long) {
        withContext(Dispatchers.Default) {
            database.databaseQueries.deleteSession(sessionId)
        }
    }

    // Runs

    fun startRunningSession(): Long {
        val startDate = Clock.System.now()
        return database.transactionWithResult {
            database.databaseQueries.insertRunSession(startDate = startDate, endDate = null)
            database.databaseQueries.lastInsertId().executeAsOne()
        }

    }

    fun stopRunningSession(sessionId: Long) {
        val endDate = Clock.System.now()
        database.databaseQueries.updateEndDate(
            endDate = endDate,
            id = sessionId
        )
    }

    fun addLocationToSession(sessionId: Long, latitude: Double, longitude: Double) {
        database.databaseQueries.insertLocation(
            sessionId = sessionId,
            latitude = latitude,
            longitude = longitude,
            timestamp = Clock.System.now()
        )
    }

    fun getSessionLocations(sessionId: Long): Flow<List<RunSessionLocationEntity>> {
        return database.databaseQueries.getLocationsForRunSession(sessionId).asFlow().mapToList(Dispatchers.Default)
    }
}