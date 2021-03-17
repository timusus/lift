package com.acompany.lift.data.model

import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter

object Mapper {

    fun comacompanyliftdata.Routine.toRoutine(exercises: List<RoutineExercise>): Routine {
        return Routine(
            id = id,
            order = sort_order,
            name = name,
            exercises = exercises
        )
    }

    fun comacompanyliftdata.RoutineExercise.toRoutineExercise(exercise: Exercise): RoutineExercise {
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

    fun comacompanyliftdata.Exercise.toExercise(): Exercise {
        return Exercise(
            id = id,
            name = name,
            oneRepMax = one_rep_max
        )
    }

    fun comacompanyliftdata.Session.toSession(dateAdapter: Rfc3339DateJsonAdapter, exercises: List<SessionExercise>): Session {
        return Session(
            id = id,
            startDate = dateAdapter.fromJson(startDate)!!,
            endDate = dateAdapter.fromJson(endDate)!!,
            routineId = routineId,
            exercises = exercises
        )
    }

    fun Session.toSession(dateAdapter: Rfc3339DateJsonAdapter): comacompanyliftdata.Session {
        return comacompanyliftdata.Session(
            id = id,
            startDate = dateAdapter.toJson(startDate)!!,
            endDate = dateAdapter.toJson(endDate)!!,
            routineId = routineId
        )
    }

    fun comacompanyliftdata.SessionExercise.toSessionExercise(routineExercise: RoutineExercise): SessionExercise {
        return SessionExercise(
            id = id,
            sets = sets,
            reps = reps,
            weight = weight,
            sessionId = sessionId,
            routineExercise = routineExercise
        )
    }

    fun SessionExercise.toSessionExercise(sessionId: Long): comacompanyliftdata.SessionExercise {
        return comacompanyliftdata.SessionExercise(
            id = id,
            sets = sets,
            reps = reps,
            weight = weight,
            sessionId = sessionId,
            routineExerciseId = routineExercise.id
        )
    }

    fun RoutineExercise.toSessionExercise(): SessionExercise {
        return SessionExercise(
            id = 0,
            sets = sets,
            reps = reps,
            weight = weight,
            sessionId = 0,
            routineExercise = this
        )
    }
}