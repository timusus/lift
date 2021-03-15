package com.acompany.lift.data.model

import java.util.*

data class Session(
    val id: Long,
    val createdAt: Date,
    val routineId: Long,
    val exercises: List<SessionExercise>
)