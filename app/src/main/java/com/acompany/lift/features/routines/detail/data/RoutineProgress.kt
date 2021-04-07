package com.acompany.lift.features.routines.detail.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

sealed class RoutineProgress : Parcelable {

    @Parcelize
    object None : RoutineProgress()

    @Parcelize
    class InProgress(val startDate: Date, val currentRoutineExerciseId: Long) : RoutineProgress()

    @Parcelize
    class Complete(val startDate: Date, val shouldSave: Boolean) : RoutineProgress()
}