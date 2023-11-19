package com.simplecityapps.lift.common.model

import kotlinx.datetime.Instant

data class SessionExercise(
    val id: String,
    val sets: Int,
    val reps: Int,
    val weight: Float?,
    val sessionId: String,
    val routineExercise: RoutineExercise,
    val currentSet: Int = 0,
    val endDate: Instant? = null
) {
    val isComplete = endDate != null
}