package com.acompany.weightr.features.exercises.data

import com.acompany.data.model.Exercise

object DummyExercises {

    val exercises = listOf(
        Exercise(
            id = 1,
            name = "Squat",
            sessionId = 0,
            sets = 5,
            reps = 3,
            percentageOneRepMax = null
        ),
        Exercise(
            id = 2,
            name = "Deadlift",
            sessionId = 0,
            sets = 5,
            reps = 3,
            percentageOneRepMax = null
        ),
        Exercise(
            id = 3,
            name = "Vertical Push",
            sessionId = 1,
            sets = 5,
            reps = 3,
            percentageOneRepMax = null
        ),
        Exercise(
            id = 4,
            name = "Vertical Pull",
            sessionId = 1,
            sets = 5,
            reps = 3,
            percentageOneRepMax = null
        )
    )
}