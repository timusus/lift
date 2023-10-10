package com.simplecityapps.shuttle.common.network.models

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseJson(
    val id: Long,
    val name: String,
    val one_rep_max: Float?,
)