package com.simplecityapps.lift.common.model

data class RoutineExercise(
    val id: String,
    val order: Int,
    val sets: Int,
    val reps: Int,
    val percentOneRepMax: Float?,
    val weight: Float?,
    val routineId: String,
    val exercise: Exercise,
    val restPeriod: Int = 90
)