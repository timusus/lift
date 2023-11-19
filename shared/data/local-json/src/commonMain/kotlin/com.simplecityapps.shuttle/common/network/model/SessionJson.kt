package com.simplecityapps.shuttle.common.network.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class SessionJson(
    val id: Long,
    val startDate: Instant,
    val endDate: Instant?,
    val routineId: Long,
    val currentExerciseId: Long?,
)