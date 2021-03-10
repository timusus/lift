package com.acompany.data.model

object Mapper {

    fun comacompanyweightrdata.Routine.toRoutine(exercises: () -> List<RoutineExercise>): Routine {
        return Routine(id, sort_order, name, exercises())
    }

    fun comacompanyweightrdata.RoutineExercise.toRoutineExercise(exercise: () -> Exercise): RoutineExercise {
        return RoutineExercise(id, sort_order, sets, reps, percent_one_rep_max, routineId, exercise())
    }

    fun comacompanyweightrdata.Exercise.toExercise(): Exercise {
        return Exercise(id, name, one_rep_max)
    }
}