package com.simplecityapps.lift.common.model

import kotlinx.datetime.Clock

fun Routine.toSession(): Session {
    val sessionExercises = exercises.map { it.toSessionExercise() }
    return Session(
        id = 0,
        startDate = Clock.System.now(),
        endDate = null,
        routine = this,
        sessionExercises = sessionExercises,
        currentExercise = sessionExercises.first()
    )
}

fun RoutineExercise.toSessionExercise(): SessionExercise {
    return SessionExercise(
        id = 0,
        sets = sets,
        reps = reps,
        weight = weight,
        sessionId = 0,
        routineExercise = this,
        currentSet = 0,
        endDate = null
    )
}
