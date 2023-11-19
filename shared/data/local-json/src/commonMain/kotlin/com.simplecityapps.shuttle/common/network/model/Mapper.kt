import com.simplecityapps.lift.common.model.Exercise
import com.simplecityapps.lift.common.model.Routine
import com.simplecityapps.lift.common.model.RoutineExercise
import com.simplecityapps.lift.common.model.Session
import com.simplecityapps.lift.common.model.SessionExercise
import com.simplecityapps.lift.common.utils.UuidGenerator
import com.simplecityapps.shuttle.common.network.model.ExerciseJson
import com.simplecityapps.shuttle.common.network.model.RoutineExerciseJson
import com.simplecityapps.shuttle.common.network.model.RoutineJson
import com.simplecityapps.shuttle.common.network.model.SessionExerciseJson
import com.simplecityapps.shuttle.common.network.model.SessionJson

fun RoutineJson.toRoutine(exercises: List<RoutineExercise>): Routine {
    return Routine(
        id = id.toUUID(),
        order = sort_order,
        name = name,
        exercises = exercises
    )
}

fun RoutineExerciseJson.toRoutineExercise(exercise: Exercise): RoutineExercise {
    return RoutineExercise(
        id = id.toUUID(),
        order = sort_order,
        sets = sets,
        reps = reps,
        percentOneRepMax = percent_one_rep_max,
        weight = weight,
        routineId = routineId.toUUID(),
        exercise = exercise
    )
}

fun ExerciseJson.toExercise(): Exercise {
    return Exercise(
        id = id.toUUID(),
        name = name,
        oneRepMax = one_rep_max
    )
}

fun SessionJson.toSession(routine: Routine, exercises: List<SessionExercise>): Session {
    return Session(
        id = id.toUUID(),
        startDate = startDate,
        endDate = endDate,
        routine = routine,
        sessionExercises = exercises,
        currentExercise = null
    )
}

fun SessionExerciseJson.toSessionExercise(routineExercise: RoutineExercise): SessionExercise {
    return SessionExercise(
        id = id.toUUID(),
        sets = sets ?: 0,
        reps = reps ?: 0,
        weight = weight,
        sessionId = sessionId.toUUID(),
        routineExercise = routineExercise,
        currentSet = currentSet,
        endDate = endDate
    )
}

fun Long.toUUID(): String {
    return UuidGenerator.generateSeededUuid(this)
}