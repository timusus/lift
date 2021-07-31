package com.acompany.lift.features.routines.detail.data

import android.os.Parcelable
import com.acompany.lift.data.model.Routine
import kotlinx.parcelize.Parcelize
import java.util.*

sealed class RoutineProgress(open val routine: Routine, open val startDate: Date) : Parcelable {

    @Parcelize
    class InProgress(
        override val routine: Routine,
        override val startDate: Date,
        val exerciseProgress: List<ExerciseProgress>
    ) : RoutineProgress(routine, startDate)

    @Parcelize
    class Complete(
        override val routine: Routine,
        override val startDate: Date
    ) : RoutineProgress(routine, startDate)
}