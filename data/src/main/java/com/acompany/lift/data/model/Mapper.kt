package com.acompany.lift.data.model

object Mapper {

    fun comacompanyliftdata.Routine.toRoutine(exercises: () -> List<RoutineExercise>): Routine {
        return Routine(id, sort_order, name, exercises())
    }

    fun comacompanyliftdata.RoutineExercise.toRoutineExercise(exercise: () -> Exercise): RoutineExercise {
        return RoutineExercise(id, sort_order, sets, reps, percent_one_rep_max, weight, routineId, exercise())
    }

    fun comacompanyliftdata.Exercise.toExercise(): Exercise {
        return Exercise(id, name, one_rep_max)
    }
}