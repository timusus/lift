package com.acompany.lift.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoutineExercise(
    val id: Long,
    val order: Int,
    val sets: Int,
    val reps: Int,
    val percentOneRepMax: Float?,
    val weight: Float?,
    val routineId: Long,
    val exercise: Exercise,
    val restPeriod: Int = 90
) : Parcelable