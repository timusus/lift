package com.simplecityapps.lift.model

import com.simplecityapps.lift.common.model.Exercise
import com.simplecityapps.lift.common.model.Location
import com.simplecityapps.lift.common.model.Routine
import com.simplecityapps.lift.common.model.RoutineExercise
import com.simplecityapps.lift.common.model.Session
import com.simplecityapps.lift.common.model.SessionExercise
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

// Routines

fun RoutineEntity.toRoutine(exercises: List<RoutineExercise>): Routine {
    return Routine(
        id = id,
        order = sort_order,
        name = name,
        exercises = exercises
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

// Run

fun RunSessionLocationEntity.toLocation(): Location {
    return Location(
        timeStamp = timestamp,
        latitude = latitude,
        longitude = longitude
    )
}
