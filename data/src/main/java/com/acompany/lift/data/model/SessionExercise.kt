package com.acompany.lift.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SessionExercise(
    val id: Long,
    val sets: Int?,
    val reps: Int?,
    val weight: Float?,
    val sessionId: Long,
    val routineExercise: RoutineExercise
) : Parcelable