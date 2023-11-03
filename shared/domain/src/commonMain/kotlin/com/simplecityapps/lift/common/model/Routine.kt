package com.simplecityapps.lift.common.model

data class Routine(
    val id: Long,
    val order: Int,
    val name: String,
    val exercises: List<RoutineExercise>
)