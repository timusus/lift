package com.simplecityapps.lift.android.ui.fake

import com.simplecityapps.lift.common.model.Exercise
import com.simplecityapps.lift.common.model.Routine
import com.simplecityapps.lift.common.model.RoutineExercise
import com.simplecityapps.lift.common.model.Session
import com.simplecityapps.lift.common.model.toSessionExercise
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.days

object FakeData {
    val routineExercises = listOf(
        RoutineExercise(
            id = 1,
            order = 0,
            sets = 3,
            reps = 5,
            percentOneRepMax = 0.825f,
            weight = 90f,
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
            weight = 120f,
            routineId = 1,
            exercise = Exercise(
                id = 2,
                name = "Deadlift",
                oneRepMax = 120f
            )
        ),
        RoutineExercise(
            id = 3,
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
            id = 4,
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
            id = 5,
            order = 0,
            sets = 3,
            reps = 12,
            percentOneRepMax = 0.825f,
            weight = 80f,
            routineId = 2,
            exercise = Exercise(
                id = 5,
                name = "Horizontal Push",
                oneRepMax = 85f
            )
        ),
        RoutineExercise(
            id = 6,
            order = 1,
            sets = 3,
            reps = 12,
            percentOneRepMax = null,
            weight = 70f,
            routineId = 2,
            exercise = Exercise(
                id = 6,
                name = "Horizontal Pull",
                oneRepMax = null
            )
        ),
        RoutineExercise(
            id = 7,
            order = 2,
            sets = 3,
            reps = 12,
            percentOneRepMax = 0.725f,
            weight = 50f,
            routineId = 2,
            exercise = Exercise(
                id = 7,
                name = "Vertical Push",
                oneRepMax = 70f
            )
        ),
        RoutineExercise(
            id = 8,
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
            id = 9,
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
        ),
        RoutineExercise(
            id = 10,
            order = 0,
            sets = 3,
            reps = 8,
            percentOneRepMax = null,
            weight = 40f,
            routineId = 3,
            exercise = Exercise(
                id = 9,
                name = "Hip hinge variant",
                oneRepMax = null
            )
        ),
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

    val sessionExercises = routineExercises
        .map { routineExercise ->
            routineExercise.toSessionExercise().copy(id = routineExercise.id)
        }

    val sessions = listOf(
        Session(
            id = 1,
            startDate = Clock.System.now().minus(24.days),
            endDate = Clock.System.now(),
            routine = routines.first(),
            sessionExercises = routineExercises.filter { it.routineId == routines.first().id }.map { it.toSessionExercise() },
            currentExercise = routineExercises.filter { it.routineId == routines.first().id }.map { it.toSessionExercise() }.first()
        ),
        Session(
            id = 2,
            startDate = Clock.System.now().minus(3.days),
            endDate = Clock.System.now(),
            routine = routines[1],
            sessionExercises = routineExercises.filter { it.routineId == routines[1].id }.map { it.toSessionExercise() },
            currentExercise = routineExercises.filter { it.routineId == routines[1].id }.map { it.toSessionExercise() }.first()
        ),
        Session(
            id = 3,
            startDate = Clock.System.now().minus(4.days),
            endDate = null,
            routine = routines[2],
            sessionExercises = routineExercises.filter { it.routineId == routines[2].id }.map { it.toSessionExercise() },
            currentExercise = routineExercises.filter { it.routineId == routines[2].id }.map { it.toSessionExercise() }.first()
        )
    )
}