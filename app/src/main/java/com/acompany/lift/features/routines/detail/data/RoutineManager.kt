package com.acompany.lift.features.routines.detail.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import com.acompany.lift.ExerciseProgressProto
import com.acompany.lift.RoutineProgressProto
import com.acompany.lift.data.AppRepository
import com.acompany.lift.data.model.Mapper.toSessionExercise
import com.acompany.lift.data.model.Routine
import com.acompany.lift.data.model.Session
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import java.util.*

class RoutineManager @AssistedInject constructor(
    private val appRepository: AppRepository,
    private val exerciseProgressDatastore: DataStore<ExerciseProgressProto>,
    private val routineProgressDatastore: DataStore<RoutineProgressProto>,
    @Assisted val routine: Routine
) {

    @AssistedFactory
    interface RoutineManagerFactory {
        fun createRoutineManager(routine: Routine): RoutineManager
    }

    private var routineProgress = MutableStateFlow<RoutineProgress>(
        RoutineProgress.None(routine = routine)
    )

    fun advance() {
        routineProgress.value = routineProgress.value.advance()
    }

//    fun advance(exerciseProgress: ExerciseProgress?): ExerciseProgress {
//        when (exerciseProgress) {
//            null -> {
//                currentExercise.value = ExerciseProgress.InProgress(0)
//            }
//            is ExerciseProgress.InProgress -> {
//                currentExercise.value = ExerciseProgress.Resting(_currentExercise.set)
//            }
//            is ExerciseProgress.Resting -> {
//                if (currentExerciseProgress.set < currentRoutineExercise.sets - 1) {
//                    currentExercise.value = ExerciseProgress.InProgress(currentExerciseProgress.set + 1)
//                } else {
//                    currentExercise.value = ExerciseProgress.Complete
//                    val index = routine.exercises.indexOf(currentRoutineExercise)
//                    if (index < exerciseProgressMap.size - 1) {
//                        val nextExerciseId = routine.exercises[index + 1].id
//                        exerciseProgressMap[nextExerciseId] = ExerciseProgress.InProgress(0)
//                        routineProgress.value = RoutineProgress.InProgress(
//                            startDate = _routineProgress.startDate,
//                            currentRoutineExerciseId = nextExerciseId
//                        )
//                    } else {
//                        routineProgress.value = RoutineProgress.Complete(
//                            startDate = _routineProgress.startDate
//                        )
//                    }
//                }
//            }
//            is ExerciseProgress.Complete -> {
//            }
//        }
//    }

    suspend fun save(routine: Routine) {
        when (val _routineProgress = routineProgress) {
            is RoutineProgress.Complete -> {
                appRepository.createSession(
                    session = Session(
                        id = 0,
                        startDate = _routineProgress.startDate,
                        endDate = Date(),
                        routine = routine,
                        exercises = routine.exercises.map { routineExercise ->
                            routineExercise.toSessionExercise()
                        }
                    )
                )
            }
            else -> {
                Timber.e("Cannot save incomplete session")
            }
        }
    }

//        val exerciseProgress = exerciseProgressMap.map { (exerciseId, progress) ->
//            "exerciseId/$exerciseId/progress/$progress"
//        }

//        appCoroutineScope.launch {
//            datastore.edit { it[PreferenceKeys.EXERCISE_IDS] = exerciseProgress.toSet() }
//            datastore.edit { it[PreferenceKeys.SESSION_PROGRESS] = sessionProgress }
//        }
}