package com.simplecityapps.shuttle.common.network.models

import kotlinx.serialization.Serializable

@Serializable
data class RoutineExerciseJson(
    val id: Long,
    val sort_order: Int,
    val sets: Int,
    val reps: Int,
    val percent_one_rep_max: Float?,
    val weight: Float?,
    val routineId: Long,
    val exerciseId: Long,
)