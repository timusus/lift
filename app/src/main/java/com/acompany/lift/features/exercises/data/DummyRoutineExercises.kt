package com.acompany.lift.features.exercises.data

import com.acompany.lift.data.model.Exercise
import com.acompany.lift.data.model.RoutineExercise

object DummyRoutineExercises {

    val routineExercises = listOf(
        RoutineExercise(
            id = 1L,
            order = 0,
            sets = 3,
            reps = 5,
            percentOneRepMax = 0.825f,
            weight = null,
            routineId = 1L,
            exercise = Exercise(
                id = 1L,
                name = "Squat",
                oneRepMax = 80f
            )
        ),
        RoutineExercise(
            id = 2L,
            order = 1,
            sets = 3,
            reps = 5,
            percentOneRepMax = 0.825f,
            weight = null,
            routineId = 1L,
            exercise = Exercise(
                id = 2L,
                name = "Deadlift",
                oneRepMax = 120f
            )
        ),
        RoutineExercise(
            id = 2L,
            order = 2,
            sets = 3,
            reps = 8,
            percentOneRepMax = null,
            weight = null,
            routineId = 1L,
            exercise = Exercise(
                id = 3L,
                name = "Single Leg",
                oneRepMax = null
            )
        ),
        RoutineExercise(
            id = 2L,
            order = 3,
            sets = 3,
            reps = 12,
            percentOneRepMax = null,
            weight = null,
            routineId = 1L,
            exercise = Exercise(
                id = 4L,
                name = "Standing calf-raises",
                oneRepMax = null
            )
        ),
        RoutineExercise(
            id = 2L,
            order = 0,
            sets = 3,
            reps = 12,
            percentOneRepMax = 0.825f,
            weight = null,
            routineId = 2L,
            exercise = Exercise(
                id = 5L,
                name = "Horizontal Push",
                oneRepMax = 85f
            )
        ),
        RoutineExercise(
            id = 2L,
            order = 1,
            sets = 3,
            reps = 12,
            percentOneRepMax = null,
            weight = null,
            routineId = 2L,
            exercise = Exercise(
                id = 6L,
                name = "Horizontal Pull",
                oneRepMax = null
            )
        ),
        RoutineExercise(
            id = 2L,
            order = 2,
            sets = 3,
            reps = 12,
            percentOneRepMax = 0.725f,
            weight = null,
            routineId = 2L,
            exercise = Exercise(
                id = 7L,
                name = "Vertical Push",
                oneRepMax = 70f
            )
        ),
        RoutineExercise(
            id = 2L,
            order = 3,
            sets = 3,
            reps = 12,
            percentOneRepMax = null,
            weight = null,
            routineId = 2L,
            exercise = Exercise(
                id = 8L,
                name = "Vertical Pull",
                oneRepMax = null
            )
        ),
        RoutineExercise(
            id = 2L,
            order = 4,
            sets = 3,
            reps = 12,
            percentOneRepMax = null,
            weight = null,
            routineId = 2L,
            exercise = Exercise(
                id = 9L,
                name = "Flys",
                oneRepMax = null
            )
        )
    )
}