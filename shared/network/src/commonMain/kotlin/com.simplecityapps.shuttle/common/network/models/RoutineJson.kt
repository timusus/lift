package com.simplecityapps.shuttle.common.network.models

import kotlinx.serialization.Serializable

@Serializable
data class RoutineJson(
    val id: Long,
    val sort_order: Int,
    val name: String,
)