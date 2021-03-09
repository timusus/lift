package com.acompany.weightr.features.exercises.data

import com.acompany.data.model.Exercise

object DummyExercises {

    val exercises = listOf(
        Exercise(
            name = "Squat",
            sessionId = 0,
            sets = 5,
            reps = 3,
            percentageOneRepMax = null
        ),
        Exercise(
            name = "Deadlift",
            sessionId = 0,
            sets = 5,
            reps = 3,
            percentageOneRepMax = null
        ),
        Exercise(
            name = "Vertical Push",
            sessionId = 1,
            sets = 5,
            reps = 3,
            percentageOneRepMax = null
        ),
        Exercise(
            name = "Vertical Pull",
            sessionId = 1,
            sets = 5,
            reps = 3,
            percentageOneRepMax = null
        )
    )
}