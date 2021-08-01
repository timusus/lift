package com.acompany.lift.features.routines.detail.data

import android.os.Parcelable
import com.acompany.lift.data.model.RoutineExercise
import kotlinx.parcelize.Parcelize

sealed class ExerciseProgress(open val exercise: RoutineExercise) : Parcelable {

    @Parcelize
    class None(override val exercise: RoutineExercise) : ExerciseProgress(exercise)

    @Parcelize
    class InProgress(override val exercise: RoutineExercise, val set: Int) : ExerciseProgress(exercise)

    @Parcelize
    class Resting(override val exercise: RoutineExercise, val set: Int) : ExerciseProgress(exercise)

    @Parcelize
    class Complete(override val exercise: RoutineExercise) : ExerciseProgress(exercise)
}

fun ExerciseProgress.advance(): ExerciseProgress {
    return when (this) {
        is ExerciseProgress.None -> {
            ExerciseProgress.InProgress(exercise, 0)
        }
        is ExerciseProgress.InProgress -> {
            ExerciseProgress.Resting(exercise, set)
        }
        is ExerciseProgress.Resting -> {
            if (set <= exercise.sets - 1) {
                ExerciseProgress.InProgress(exercise, set + 1)
            } else {
                ExerciseProgress.Complete(exercise)
            }
        }
        is ExerciseProgress.Complete -> {
            this
        }
    }
}