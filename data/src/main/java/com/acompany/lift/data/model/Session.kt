package com.acompany.lift.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Session(
    val id: Long,
    val startDate: Date,
    val endDate: Date,
    val routine: Routine,
    val exercises: List<SessionExercise>
) : Parcelable