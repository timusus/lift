package com.simplecityapps.lift.repository

import com.simplecityapps.lift.model.Routine
import com.simplecityapps.lift.model.RoutineExercise
import com.simplecityapps.lift.model.toRoutine
import com.simplecityapps.lift.model.toRoutineEntity
import com.simplecityapps.lift.model.toRoutineExercise
import com.simplecityapps.shuttle.common.database.AppDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class LocalRoutineRepository(
    private val dataSource: AppDataSource,
    private val exerciseRepository: ExerciseRepository
) : RoutineRepository {

    // Routine Exercises

    override suspend fun createRoutineExercise(
        routineExercise: RoutineExercise,
        generateId: Boolean
    ) {
        dataSource.createRoutineExercise(
            routineExercise = routineExercise.toRoutineExercise(),
            generateId = generateId
        )
    }

    override fun getRoutineExercises(routineIds: Collection<Long>?): Flow<List<RoutineExercise>> {
        return combine(
            dataSource.getRoutineExercises(routineIds),
            exerciseRepository.getAllExercises(),
        ) { routineExercises, exercises ->
            routineExercises.map { routineExercise ->
                routineExercise.toRoutineExercise(exercises.first { exercise -> exercise.id == routineExercise.exerciseId })
            }
        }
    }

    override suspend fun updateRoutineExercisePercentOneRepMax(id: Long, percentOneRepMax: Float?) {
        dataSource.updateRoutineExercisePercentOneRepMax(
            id = id, percentOneRepMax = percentOneRepMax
        )
    }

    override suspend fun updateRoutineExerciseWeight(id: Long, weight: Float?) {
        dataSource.updateRoutineExerciseWeight(
            id = id, weight = weight
        )
    }

    override suspend fun updateRoutineExerciseOneRepMax(id: Long, oneRepMax: Float?) {
        dataSource.updateExerciseOneRepMax(
            id = id, oneRepMax = oneRepMax
        )
    }

    // Routines

    override suspend fun createRoutine(
        routine: Routine,
        generateId: Boolean
    ) {
        dataSource.createRoutine(
            routine = routine.toRoutineEntity(),
            generateId = generateId
        )
    }

    override fun getRoutines(ids: Collection<Long>?): Flow<List<Routine>> {
        return combine(
            dataSource.getRoutines(ids),
            getRoutineExercises()
        ) { routines, routineExercises ->
            routines.map { routine ->
                routine.toRoutine(routineExercises.filter { it.routineId == routine.id })
            }
        }
    }

    override fun getRoutine(id: Long): Flow<Routine> {
        return combine(
            dataSource.getRoutine(id),
            getRoutineExercises()
        ) { routine, routineExercises ->
            routine.toRoutine(routineExercises.filter { it.routineId == routine.id })
        }
    }
}

