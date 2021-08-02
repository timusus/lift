package com.acompany.lift.features.routines.detail.data

import com.acompany.lift.data.AppRepository
import com.acompany.lift.data.model.Routine
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow

class RoutineManager @AssistedInject constructor(
    private val appRepository: AppRepository,
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

    suspend fun save(routine: Routine) {
//        when (val _routineProgress = routineProgress) {
//            is RoutineProgress.Complete -> {
//                appRepository.createSession(
//                    session = Session(
//                        id = 0,
//                        startDate = _routineProgress.startDate,
//                        endDate = Date(),
//                        routine = routine,
//                        exercises = routine.exercises.map { routineExercise ->
//                            routineExercise.toSessionExercise()
//                        }
//                    )
//                )
//            }
//            else -> {
//                Timber.e("Cannot save incomplete session")
//            }
//        }
    }

//        val exerciseProgress = exerciseProgressMap.map { (exerciseId, progress) ->
//            "exerciseId/$exerciseId/progress/$progress"
//        }

//        appCoroutineScope.launch {
//            datastore.edit { it[PreferenceKeys.EXERCISE_IDS] = exerciseProgress.toSet() }
//            datastore.edit { it[PreferenceKeys.SESSION_PROGRESS] = sessionProgress }
//        }
}