package com.acompany.lift.data.model

data class Routine(
    val id: Long,
    val order: Int,
    val name: String,
    val exercises: List<RoutineExercise>
)