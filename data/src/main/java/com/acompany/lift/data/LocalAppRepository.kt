package com.acompany.lift.data

import com.acompany.lift.data.model.*
import com.acompany.lift.data.model.Mapper.toExercise
import com.acompany.lift.data.model.Mapper.toRoutine
import com.acompany.lift.data.model.Mapper.toRoutineExercise
import com.acompany.lift.data.model.Mapper.toSession
import com.acompany.lift.data.model.Mapper.toSessionExercise
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LocalAppRepository(
    private val databaseHelper: DatabaseHelper,
    private val dispatcher: CoroutineDispatcher,
    private val dateAdapter: Rfc3339DateJsonAdapter
) : AppRepository {

    private val database by lazy {
        databaseHelper.database
    }

    override fun getRoutines(ids: Collection<Long>?): Flow<List<Routine>> {
        val selectRoutineQuery = (ids?.let {
            database.databaseQueries.selectRoutines(ids)
        } ?: database.databaseQueries.selectAllRoutines())
            .asFlow().mapToList()

        return combine(
            getRoutineExercises(),
            selectRoutineQuery
        ) { routineExercises, routines ->
            routines.map { routine -> routine.toRoutine(routineExercises.filter { it.routineId == routine.id }) }
        }
    }

    override fun getRoutineExercises(routineIds: Collection<Long>?): Flow<List<RoutineExercise>> {
        val selectRoutineExercisesQuery = (routineIds?.let {
            database.databaseQueries.selectRoutineExercises(routineIds)
        } ?: database.databaseQueries.selectAllRoutineExercises())
            .asFlow().mapToList()

        return combine(
            getAllExercises(),
            selectRoutineExercisesQuery
        ) { exercises, routineExercises ->
            routineExercises
                .map { routineExercise -> routineExercise.toRoutineExercise(exercises.first { exercise -> exercise.id == routineExercise.exerciseId }) }
        }
    }

    override fun getAllExercises(): Flow<List<Exercise>> {
        return database.databaseQueries.selectAllExercises().asFlow().mapToList()
            .map { list -> list.map { exercise -> exercise.toExercise() } }
    }

    override fun getSessions(sessionIds: Collection<Long>?): Flow<List<Session>> {
        val selectSessionQuery = (sessionIds?.let {
            database.databaseQueries.selectSessions(sessionIds)
        } ?: database.databaseQueries.selectAllSessions())
            .asFlow().mapToList()

        return combine(
            getRoutines(),
            getSessionExercises(),
            selectSessionQuery
        ) { routines, sessionExercises, sessions ->
            sessions.map { session ->
                session.toSession(
                    dateAdapter = dateAdapter, routine = routines.first { session.routineId == it.id },
                    exercises = sessionExercises.filter { it.sessionId == session.id })
            }
        }
    }

    override fun getSessionExercises(): Flow<List<SessionExercise>> {
        return combine(
            getRoutineExercises(),
            database.databaseQueries.selectAllSessionExercises().asFlow().mapToList()
        ) { routineExercises, sessionExercises ->
            sessionExercises.map { sessionExercise -> sessionExercise.toSessionExercise(routineExercises.first { it.id == sessionExercise.routineExerciseId }) }
        }
    }

    override suspend fun updateRoutineExercisePercentOneRepMax(id: Long, percentOneRepMax: Float?) {
        withContext(dispatcher) {
            database.databaseQueries.updateRoutineExercisePercentOneRepMax(percentOneRepMax, id)
        }
    }

    override suspend fun updateRoutineExerciseWeight(id: Long, weight: Float?) {
        withContext(dispatcher) {
            database.databaseQueries.updateRoutineExerciseWeight(weight, id)
        }
    }

    override suspend fun updateExerciseOneRepMax(id: Long, oneRepMax: Float?) {
        withContext(dispatcher) {
            database.databaseQueries.updateExerciseOneRepMax(oneRepMax, id)
        }
    }

    override suspend fun createSession(session: Session) {
        withContext(dispatcher) {
            val sessionId = database.transactionWithResult<Long> {
                database.databaseQueries.createSession(session.toSession(dateAdapter))
                database.databaseQueries.lastInsertId().executeAsOne()
            }
            database.transaction {
                session.routine.exercises.map { routineExercise -> routineExercise.toSessionExercise() }.forEach {
                    database.databaseQueries.createSessionExercise(it.toSessionExercise(sessionId))
                }
            }
        }
    }

    override suspend fun deleteSession(session: Session) {
        withContext(dispatcher) {
            database.databaseQueries.deleteSession(session.id)
        }
    }

    override suspend fun deleteSessionExercises(session: Session) {
        withContext(dispatcher) {
            database.databaseQueries.deleteExercisesForSession(session.id)
        }
    }
}