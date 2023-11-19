package com.simplecityapps.lift.common.model

import com.simplecityapps.lift.common.utils.UuidGenerator
import kotlinx.datetime.Clock

fun Routine.toSession(): Session {
    val sessionId = UuidGenerator.generateUuid()
    val sessionExercises = exercises.map { it.toSessionExercise(sessionId) }
    return Session(
        id = sessionId,
        startDate = Clock.System.now(),
        endDate = null,
        routine = this,
        sessionExercises = sessionExercises,
        currentExercise = sessionExercises.first()
    )
}

fun RoutineExercise.toSessionExercise(sessionId: String): SessionExercise {
    return SessionExercise(
        id = UuidGenerator.generateUuid(),
        sets = sets,
        reps = reps,
        weight = weight,
        sessionId = sessionId,
        routineExercise = this,
        currentSet = 0,
        endDate = null
    )
}
