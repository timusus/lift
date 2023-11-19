package com.simplecityapps.shuttle.common.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseJson(
    val id: Long,
    val name: String,
    val one_rep_max: Float?,
)