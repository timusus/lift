package com.acompany.lift.features.routines.data

import com.acompany.lift.data.model.Routine
import com.acompany.lift.features.exercises.data.DummyRoutineExercises

object DummyRoutines {
    val routines = listOf(
        Routine(id = 1L, order = 0, name = "Lower Body (Strength)", exercises = DummyRoutineExercises.routineExercises.filter { it.routineId == 1L }),
        Routine(id = 2L, order = 1, name = "Upper Body (Strength)", exercises = DummyRoutineExercises.routineExercises.filter { it.routineId == 2L }),
        Routine(id = 3L, order = 2, name = "Lower Body (Volume)", exercises = DummyRoutineExercises.routineExercises.filter { it.routineId == 3L }),
        Routine(id = 4L, order = 3, name = "Upper Body (Volume)", exercises = DummyRoutineExercises.routineExercises.filter { it.routineId == 4L })
    )
}