package com.acompany.lift.data.model

data class SessionExercise(
    val id: Long,
    val sets: Int?,
    val reps: Int?,
    val weight: Float?,
    val sessionId: Long,
    val routineExerciseID: Long
)