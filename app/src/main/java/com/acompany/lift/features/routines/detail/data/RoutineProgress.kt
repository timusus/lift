package com.acompany.lift.features.routines.detail.data

import android.os.Parcelable
import com.acompany.lift.data.model.Routine
import kotlinx.parcelize.Parcelize
import java.util.*

sealed class RoutineProgress(
    open val routine: Routine
) : Parcelable {

    @Parcelize
    class None(
        override val routine: Routine
    ) : RoutineProgress(
        routine = routine
    )

    @Parcelize
    class InProgress(
        override val routine: Routine,
        val exerciseProgressList: List<ExerciseProgress>,
        val currentExerciseProgress: ExerciseProgress,
        val startDate: Date
    ) : RoutineProgress(
        routine = routine
    )

    @Parcelize
    class Complete(
        override val routine: Routine,
        val startDate: Date
    ) : RoutineProgress(
        routine = routine
    )
}

fun RoutineProgress.advance(): RoutineProgress {
    return when (this) {
        is RoutineProgress.None -> {
            val exerciseProgressList = routine.exercises.map { exercise -> ExerciseProgress.InProgress(exercise, 0) }
            RoutineProgress.InProgress(
                routine = routine,
                exerciseProgressList = exerciseProgressList,
                currentExerciseProgress = exerciseProgressList.first(),
                startDate = Date()
            )
        }
        is RoutineProgress.InProgress -> {
            val exerciseProgressList = exerciseProgressList.toMutableList()
            val currentIndex = exerciseProgressList.indexOf(currentExerciseProgress)

            if (currentExerciseProgress is ExerciseProgress.Complete) {
                if (exerciseProgressList.all { it is ExerciseProgress.Complete }) {
                    RoutineProgress.Complete(routine, startDate)
                } else {
                    // move to next exercise

                    RoutineProgress.InProgress(routine, exerciseProgressList, currentExerciseProgress, startDate)
                }
            } else {
                // progress current exercise
                RoutineProgress.InProgress(routine, exerciseProgressList, currentExerciseProgress, startDate)

            }
        }
        is RoutineProgress.Complete -> {
            return this
        }
    }
}