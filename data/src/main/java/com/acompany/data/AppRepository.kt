package com.acompany.data

import com.acompany.data.model.Exercise
import com.acompany.data.model.Mapper.toExercise
import com.acompany.data.model.Mapper.toRoutineExercise
import com.acompany.data.model.Mapper.toRoutine
import com.acompany.data.model.Routine
import com.acompany.data.model.RoutineExercise
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AppRepository(
    private val database: Database,
    private val dispatcher: CoroutineDispatcher
) {

    fun getRoutines(): Flow<List<Routine>> {
        return combine(
            getRoutineExercises(),
            database.databaseQueries.selectAllRoutines().asFlow().mapToList()
        ) { routineExercises, routines ->
            routines.map { routine -> routine.toRoutine { routineExercises.filter { it.routineId == routine.id } } }
        }
    }

    fun getRoutineExercises(): Flow<List<RoutineExercise>> {
        return combine(
            getAllExercises(),
            database.databaseQueries.selectAllRoutineExercises().asFlow().mapToList()
        ) { exercises, routineExercises ->
            routineExercises
                .map { routineExercise -> routineExercise.toRoutineExercise { exercises.first { exercise -> exercise.id == routineExercise.exerciseId } } }
        }
    }

    fun getRoutineExercises(routineIds: Collection<Long>): Flow<List<RoutineExercise>> {
        return combine(
            getAllExercises(),
            database.databaseQueries.selectRoutineExercises(routineIds).asFlow().mapToList()
        ) { exercises, routineExercises ->
            routineExercises
                .map { routineExercise -> routineExercise.toRoutineExercise { exercises.first { exercise -> exercise.id == routineExercise.exerciseId } } }
        }
    }

    fun getAllExercises(): Flow<List<Exercise>> {
        return database.databaseQueries.selectAllExercises().asFlow().mapToList()
            .map { list -> list.map { exercise -> exercise.toExercise() } }
    }

    suspend fun updateExercise(id: Long, oneRepMax: Float?) {
        withContext(dispatcher) {
            database.databaseQueries.updateExercise(oneRepMax, id)
        }
    }
}