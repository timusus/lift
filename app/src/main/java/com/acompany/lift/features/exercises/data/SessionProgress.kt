package com.acompany.lift.features.exercises.data

import android.os.Parcelable
import com.acompany.lift.data.model.RoutineExercise
import kotlinx.parcelize.Parcelize
import java.util.*

sealed class SessionProgress : Parcelable {
    @Parcelize
    object None : SessionProgress()

    @Parcelize
    class InProgress(val startDate: Date, val currentExercise: RoutineExercise) : SessionProgress()

    @Parcelize
    class Complete(val startDate: Date, val shouldSave: Boolean) : SessionProgress()
}