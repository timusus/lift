package com.acompany.lift.common

import com.acompany.lift.data.model.Exercise
import com.acompany.lift.data.model.RoutineExercise
import com.acompany.lift.data.model.Session
import java.lang.Math.round

object RoutineExerciseHelper {

    /**
     * Retrieve an initial weight for this exercise, derived from the exercise's [Exercise.oneRepMax], multiplied by ([RoutineExercise.percentOneRepMax] or 1, if unavailable)
     * If no [Exercise.oneRepMax] is supplied, return null
     */
    fun RoutineExercise.initialWeight(): Float? {
        return exercise.oneRepMax?.let { oneRepMax ->
            round(0.4f * oneRepMax * (percentOneRepMax ?: 1f)) / 0.4f
        }
    }
}

object SessionHelper {

    fun List<Session>.totalVolume(): Float {
        return map { it.volume() }.reduce { acc, fl -> acc + fl }
    }

    fun Session.volume(): Float {
        return exercises.map { sessionExercise ->
            val weight = sessionExercise.weight ?: 0f
            val reps = sessionExercise.reps ?: 0
            val sets = sessionExercise.sets ?: 0
            weight * reps * sets
        }.reduce { acc, fl -> acc + fl }
    }
}