package com.acompany.lift.data

import com.acompany.lift.data.model.Exercise
import com.acompany.lift.data.model.Mapper.toExercise
import com.acompany.lift.data.model.Mapper.toRoutineExercise
import com.acompany.lift.data.model.Mapper.toRoutine
import com.acompany.lift.data.model.Routine
import com.acompany.lift.data.model.RoutineExercise
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LocalAppRepository(
    private val database: Database,
    private val dispatcher: CoroutineDispatcher
) : AppRepository {

    override fun getAllRoutines(): Flow<List<Routine>> {
        return combine(
            getRoutineExercises(),
            database.databaseQueries.selectAllRoutines().asFlow().mapToList()
        ) { routineExercises, routines ->
            routines.map { routine -> routine.toRoutine { routineExercises.filter { it.routineId == routine.id } } }
        }
    }

    override fun getRoutines(ids: Collection<Long>): Flow<List<Routine>> {
        return combine(
            getRoutineExercises(),
            database.databaseQueries.selectRoutines(ids).asFlow().mapToList()
        ) { routineExercises, routines ->
            routines.map { routine -> routine.toRoutine { routineExercises.filter { it.routineId == routine.id } } }
        }
    }

    override fun getRoutineExercises(): Flow<List<RoutineExercise>> {
        return combine(
            getAllExercises(),
            database.databaseQueries.selectAllRoutineExercises().asFlow().mapToList()
        ) { exercises, routineExercises ->
            routineExercises
                .map { routineExercise -> routineExercise.toRoutineExercise { exercises.first { exercise -> exercise.id == routineExercise.exerciseId } } }
        }
    }

    override fun getRoutineExercises(routineIds: Collection<Long>): Flow<List<RoutineExercise>> {
        return combine(
            getAllExercises(),
            database.databaseQueries.selectRoutineExercises(routineIds).asFlow().mapToList()
        ) { exercises, routineExercises ->
            routineExercises
                .map { routineExercise -> routineExercise.toRoutineExercise { exercises.first { exercise -> exercise.id == routineExercise.exerciseId } } }
        }
    }

    override fun getAllExercises(): Flow<List<Exercise>> {
        return database.databaseQueries.selectAllExercises().asFlow().mapToList()
            .map { list -> list.map { exercise -> exercise.toExercise() } }
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
}