package com.acompany.weightr.features.exercises.components

import com.acompany.data.model.RoutineExercise
import com.acompany.data.model.Exercise

object ExerciseHelper {

    /**
     * Retrieve an initial weight for this exercise, derived from the exercise's [Exercise.oneRepMax], multiplied by ([RoutineExercise.percentOneRepMax] or 1, if unavailable)
     * If no [Exercise.oneRepMax] is supplied, return null
     */
    fun RoutineExercise.initialWeight(): Float? {
        return exercise.oneRepMax?.let { oneRepMax ->
            oneRepMax * (percentOneRepMax ?: 1f)
        }
    }

}