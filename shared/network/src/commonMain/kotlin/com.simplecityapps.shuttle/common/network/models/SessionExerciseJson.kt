package com.simplecityapps.shuttle.common.network.models

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class SessionExerciseJson(
    val id: Long,
    val sets: Int?,
    val reps: Int?,
    val weight: Float?,
    val sessionId: Long,
    val routineExerciseId: Long,
    val currentSet: Int,
    val endDate: Instant?,
)