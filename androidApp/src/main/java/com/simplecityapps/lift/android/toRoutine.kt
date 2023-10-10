package com.simplecityapps.lift.android

import com.simplecityapps.lift.model.Exercise
import com.simplecityapps.lift.model.Routine
import com.simplecityapps.lift.model.RoutineExercise
import com.simplecityapps.lift.model.Session
import com.simplecityapps.lift.model.SessionExercise
import com.simplecityapps.shuttle.common.network.models.ExerciseJson
import com.simplecityapps.shuttle.common.network.models.RoutineExerciseJson
import com.simplecityapps.shuttle.common.network.models.RoutineJson
import com.simplecityapps.shuttle.common.network.models.SessionExerciseJson
import com.simplecityapps.shuttle.common.network.models.SessionJson

fun RoutineJson.toRoutine(exercises: List<RoutineExercise>): Routine {
    return Routine(
        id = id,
        order = sort_order,
        name = name,
        exercises = exercises
    )
}

fun RoutineExerciseJson.toRoutineExercise(exercise: Exercise): RoutineExercise {
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

fun ExerciseJson.toExercise(): Exercise {
    return Exercise(
        id = id,
        name = name,
        oneRepMax = one_rep_max
    )
}

fun SessionJson.toSession(routine: Routine, exercises: List<SessionExercise>): Session {
    return Session(
        id = id,
        startDate = startDate,
        endDate = endDate,
        routine = routine,
        sessionExercises = exercises,
        currentExercise = null
    )
}

fun SessionExerciseJson.toSessionExercise(routineExercise: RoutineExercise): SessionExercise {
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