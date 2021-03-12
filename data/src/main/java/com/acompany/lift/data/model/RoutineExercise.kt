package com.acompany.lift.data.model

data class RoutineExercise(
    val id: Long,
    val order: Int,
    val sets: Int,
    val reps: Int,
    val percentOneRepMax: Float?,
    val weight: Float?,
    val routineId: Long,
    val exercise: Exercise
)