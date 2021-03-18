package com.acompany.lift.features.main.data

import com.acompany.lift.data.AppRepository
import com.acompany.lift.data.model.*
import com.acompany.lift.data.model.Mapper.toSessionExercise
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.*

class DummyAppRepository : AppRepository {

    override fun getAllRoutines(): Flow<List<Routine>> {
        return flowOf(routines)
    }

    override fun getRoutines(ids: Collection<Long>): Flow<List<Routine>> {
        return flowOf(routines.filter { ids.contains(it.id) })
    }

    override fun getRoutineExercises(): Flow<List<RoutineExercise>> {
        return flowOf(routineExercises)
    }

    override fun getRoutineExercises(routineIds: Collection<Long>): Flow<List<RoutineExercise>> {
        return flowOf(routineExercises.filter { routineIds.contains(it.routineId) })
    }

    override fun getAllExercises(): Flow<List<Exercise>> {
        return flowOf(routineExercises.map { it.exercise })
    }

    override fun getAllSessions(): Flow<List<Session>> {
        return flowOf(sessions)
    }

    override fun getAllSessionExercises(): Flow<List<SessionExercise>> {
        return flowOf(sessionExercises)
    }

    override suspend fun updateRoutineExercisePercentOneRepMax(id: Long, percentOneRepMax: Float?) {

    }

    override suspend fun updateRoutineExerciseWeight(id: Long, weight: Float?) {

    }

    override suspend fun updateExerciseOneRepMax(id: Long, oneRepMax: Float?) {

    }

    override suspend fun createSession(session: Session, routine: Routine) {

    }

    override suspend fun deleteSession(session: Session) {

    }

    override suspend fun deleteSessionExercises(session: Session) {

    }

    companion object {
        val routineExercises = listOf(
            RoutineExercise(
                id = 1,
                order = 0,
                sets = 3,
                reps = 5,
                percentOneRepMax = 0.825f,
                weight = null,
                routineId = 1,
                exercise = Exercise(
                    id = 1,
                    name = "Squat",
                    oneRepMax = 80f
                )
            ),
            RoutineExercise(
                id = 2,
                order = 1,
                sets = 3,
                reps = 5,
                percentOneRepMax = 0.825f,
                weight = null,
                routineId = 1,
                exercise = Exercise(
                    id = 2,
                    name = "Deadlift",
                    oneRepMax = 120f
                )
            ),
            RoutineExercise(
                id = 2,
                order = 2,
                sets = 3,
                reps = 8,
                percentOneRepMax = null,
                weight = null,
                routineId = 1,
                exercise = Exercise(
                    id = 3,
                    name = "Single Leg",
                    oneRepMax = null
                )
            ),
            RoutineExercise(
                id = 2,
                order = 3,
                sets = 3,
                reps = 12,
                percentOneRepMax = null,
                weight = null,
                routineId = 1,
                exercise = Exercise(
                    id = 4,
                    name = "Standing calf-raises",
                    oneRepMax = null
                )
            ),
            RoutineExercise(
                id = 2,
                order = 0,
                sets = 3,
                reps = 12,
                percentOneRepMax = 0.825f,
                weight = null,
                routineId = 2,
                exercise = Exercise(
                    id = 5,
                    name = "Horizontal Push",
                    oneRepMax = 85f
                )
            ),
            RoutineExercise(
                id = 2,
                order = 1,
                sets = 3,
                reps = 12,
                percentOneRepMax = null,
                weight = null,
                routineId = 2,
                exercise = Exercise(
                    id = 6,
                    name = "Horizontal Pull",
                    oneRepMax = null
                )
            ),
            RoutineExercise(
                id = 2,
                order = 2,
                sets = 3,
                reps = 12,
                percentOneRepMax = 0.725f,
                weight = null,
                routineId = 2,
                exercise = Exercise(
                    id = 7,
                    name = "Vertical Push",
                    oneRepMax = 70f
                )
            ),
            RoutineExercise(
                id = 2,
                order = 3,
                sets = 3,
                reps = 12,
                percentOneRepMax = null,
                weight = null,
                routineId = 2,
                exercise = Exercise(
                    id = 8,
                    name = "Vertical Pull",
                    oneRepMax = null
                )
            ),
            RoutineExercise(
                id = 2,
                order = 4,
                sets = 3,
                reps = 12,
                percentOneRepMax = null,
                weight = null,
                routineId = 2,
                exercise = Exercise(
                    id = 9,
                    name = "Flys",
                    oneRepMax = null
                )
            )
        )

        val routines = listOf(
            Routine(
                id = 1,
                order = 0,
                name = "Lower Body (Strength)",
                exercises = routineExercises.filter { it.routineId == 1L }),
            Routine(
                id = 2,
                order = 1,
                name = "Upper Body (Strength)",
                exercises = routineExercises.filter { it.routineId == 2L }),
            Routine(
                id = 3,
                order = 2,
                name = "Lower Body (Volume)",
                exercises = routineExercises.filter { it.routineId == 3L }),
            Routine(
                id = 4,
                order = 3,
                name = "Upper Body (Volume)",
                exercises = routineExercises.filter { it.routineId == 4L })
        )

        val sessionExercises = routineExercises.map { it.toSessionExercise() }

        val sessions = listOf(
            Session(
                id = 1,
                startDate = Date(Date().time - 24 * 60 * 60 * 1000),
                endDate = Date(),
                routine = routines.first(),
                exercises = sessionExercises
            )
        )
    }
}