package com.acompany.lift.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Routine(
    val id: Long,
    val order: Int,
    val name: String,
    val exercises: List<RoutineExercise>
) : Parcelable