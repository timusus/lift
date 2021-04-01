package com.acompany.lift.features.routines.detail.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class ExerciseProgress : Parcelable {
    @Parcelize
    object None : ExerciseProgress()

    @Parcelize
    class InProgress(val set: Int) : ExerciseProgress()

    @Parcelize
    class Resting(val set: Int) : ExerciseProgress()

    @Parcelize
    object Complete : ExerciseProgress()
}