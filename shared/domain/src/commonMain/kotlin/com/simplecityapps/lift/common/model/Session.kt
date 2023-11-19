package com.simplecityapps.lift.common.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.hours

data class Session(
    val id: String,
    val startDate: Instant,
    val endDate: Instant?,
    val routine: Routine,
    val sessionExercises: List<SessionExercise>,
    val currentExercise: SessionExercise?
) {
    val isComplete
        get() = endDate != null || startDate < Clock.System.now().minus(12.hours)

    val isStarted = sessionExercises.any { it.currentSet != 0 }
}

fun Session.volume(): Float {
    val volumes = sessionExercises.map { sessionExercise ->
        val weight = sessionExercise.weight ?: 0f
        val reps = sessionExercise.reps
        val sets = sessionExercise.sets
        weight * reps * sets
    }

    return if (volumes.isNotEmpty()) {
        volumes.reduce { acc, fl -> acc + fl }
    } else {
        0f
    }
}

fun List<Session>.totalVolume(): Float {
    return map { session -> session.volume() }.reduce { acc, fl -> acc + fl }
}