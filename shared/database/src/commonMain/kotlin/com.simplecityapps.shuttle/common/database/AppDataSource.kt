package com.simplecityapps.shuttle.common.database

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

class AppDataSource(
    private val database: LiftDatabase
) {

    // Exercises

    suspend fun createExercise(
        exercise: ExerciseEntity,
        generateId: Boolean = true
    ): Long {
        return withContext(Dispatchers.Default) {
            database.transactionWithResult {
                if (generateId) {
                    database.databaseQueries.createExercise(exercise)
                } else {
                    database.databaseQueries.createExerciseWithId(exercise)
                }
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
        routineExercise: RoutineExerciseEntity,
        generateId: Boolean = true
    ): Long {
        return withContext(Dispatchers.Default) {
            database.transactionWithResult {
                if (generateId) {
                    database.databaseQueries.createRoutineExercise(routineExercise)
                } else {
                    database.databaseQueries.createRoutineExerciseWithId(routineExercise)
                }
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
        routine: RoutineEntity,
        generateId: Boolean = true
    ): Long {
        return withContext(Dispatchers.Default) {
            database.transactionWithResult {
                if (generateId) {
                    database.databaseQueries.createRoutine(routine)
                } else {
                    database.databaseQueries.createRoutineWithId(routine)
                }
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
        sessionExercise: SessionExerciseEntity,
        generateId: Boolean = true
    ): Long {
        return withContext(Dispatchers.Default) {
            database.transactionWithResult {
                if (generateId) {
                    database.databaseQueries.createSessionExercise(sessionExercise)
                } else {
                    database.databaseQueries.createSessionExerciseWithId(sessionExercise)
                }
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

    suspend fun updateSessionExercise(sessionExercise: SessionExerciseEntity) {
        withContext(Dispatchers.Default) {
            database.databaseQueries.updateSessionExercise(
                sessionExerciseId = sessionExercise.id,
                currentSet = sessionExercise.currentSet,
                endDate = sessionExercise.endDate,
                weight = sessionExercise.weight
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
        session: SessionEntity,
        generateId: Boolean = true
    ): Long {
        return withContext(Dispatchers.Default) {
            database.transactionWithResult {
                if (generateId) {
                    database.databaseQueries.createSession(session)
                } else {
                    database.databaseQueries.createSessionWithId(session)
                }
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

    suspend fun updateSession(session: SessionEntity) {
        withContext(Dispatchers.Default) {
            database.databaseQueries.updateSession(
                sessionId = session.id,
                currentExerciseId = session.currentExerciseId,
                endDate = session.endDate
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
            database.databaseQueries.insertRunSession(startDate, null)
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