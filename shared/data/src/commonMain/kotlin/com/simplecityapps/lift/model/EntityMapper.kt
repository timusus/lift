package com.simplecityapps.lift.model

import comsimplecityappslift.common.database.ExerciseEntity
import comsimplecityappslift.common.database.RoutineEntity
import comsimplecityappslift.common.database.RoutineExerciseEntity
import comsimplecityappslift.common.database.RunSessionLocationEntity
import comsimplecityappslift.common.database.SessionEntity
import comsimplecityappslift.common.database.SessionExerciseEntity

// Exercises

fun ExerciseEntity.toExerciseEntity(): Exercise {
    return Exercise(
        id = id,
        name = name,
        oneRepMax = one_rep_max
    )
}

fun Exercise.toExerciseEntity(): ExerciseEntity {
    return ExerciseEntity(
        id = id,
        name = name,
        one_rep_max = oneRepMax
    )
}

// Routine Exercises

fun RoutineExerciseEntity.toRoutineExercise(exercise: Exercise): RoutineExercise {
    return RoutineExercise(
        id = id,
        order = sort_order,
        sets = sets,
        reps = reps,
        percentOneRepMax = percent_one_rep_max,
        weight = weight,
        routineId = routineId,
        exercise = exercise
    )
}

fun RoutineExercise.toRoutineExercise(): RoutineExerciseEntity {
    return RoutineExerciseEntity(
        id = id,
        sort_order = order,
        sets = sets,
        reps = reps,
        percent_one_rep_max = percentOneRepMax,
        weight = weight,
        routineId = routineId,
        exerciseId = exercise.id
    )
}

// Routines

fun RoutineEntity.toRoutine(exercises: List<RoutineExercise>): Routine {
    return Routine(
        id = id,
        order = sort_order,
        name = name,
        exercises = exercises
    )
}

fun Routine.toRoutineEntity(): RoutineEntity {
    return RoutineEntity(
        id = id,
        sort_order = order,
        name = name
    )
}

// Sessions

fun SessionEntity.toSession(routine: Routine, sessionExercises: List<SessionExercise>): Session {
    return Session(
        id = id,
        startDate = startDate,
        endDate = endDate,
        routine = routine,
        sessionExercises = sessionExercises,
        currentExercise = sessionExercises.firstOrNull { it.id == currentExerciseId }
    )
}

fun Session.toSessionEntity(): SessionEntity {
    return SessionEntity(
        id = id,
        startDate = startDate,
        endDate = endDate,
        routineId = routine.id,
        currentExerciseId = currentExercise?.id
    )
}

// Session Exercises

fun SessionExerciseEntity.toSessionExercise(routineExercise: RoutineExercise): SessionExercise {
    return SessionExercise(
        id = id,
        sets = sets ?: 0,
        reps = reps ?: 0,
        weight = weight,
        sessionId = sessionId,
        routineExercise = routineExercise,
        currentSet = currentSet,
        endDate = endDate
    )
}

fun SessionExercise.toSessionExerciseEntity(): SessionExerciseEntity {
    return SessionExerciseEntity(
        id = id,
        sets = sets,
        reps = reps,
        weight = weight,
        sessionId = sessionId,
        routineExerciseId = routineExercise.id,
        currentSet = currentSet,
        endDate = endDate
    )
}

// Routine Exercises

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

fun Routine.toSession(): Session {
    val sessionExercises = exercises.map { it.toSessionExercise() }
    return Session(
        id = 0,
        startDate = kotlinx.datetime.Clock.System.now(),
        endDate = null,
        routine = this,
        sessionExercises = sessionExercises,
        currentExercise = sessionExercises.first()
    )
}

// Run

fun RunSessionLocationEntity.toLocation(): Location {
    return Location(
        timeStamp = timestamp,
        latitude = latitude,
        longitude = longitude
    )
}

fun Location.toLocationEntity(): RunSessionLocationEntity {
    return RunSessionLocationEntity(
        id = 0,
        sessionId = 0,
        latitude = latitude,
        longitude = longitude,
        timestamp = timeStamp
    )
}