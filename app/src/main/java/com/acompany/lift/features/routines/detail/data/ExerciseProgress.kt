package com.acompany.lift.features.routines.detail.data

import android.os.Parcelable
import com.acompany.lift.data.model.RoutineExercise
import kotlinx.parcelize.Parcelize

data class ExerciseProgress(open val exercise: RoutineExercise) : Parcelable {

    @Parcelize
    class InProgress(override val exercise: RoutineExercise, val set: Int) : ExerciseProgress(exercise)

    @Parcelize
    class Resting(override val exercise: RoutineExercise, val set: Int) : ExerciseProgress(exercise)

    @Parcelize
    class Complete(override val exercise: RoutineExercise) : ExerciseProgress(exercise)
}