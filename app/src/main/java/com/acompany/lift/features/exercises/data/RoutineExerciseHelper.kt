package com.acompany.lift.features.exercises.data

import com.acompany.lift.data.model.Exercise
import com.acompany.lift.data.model.RoutineExercise
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