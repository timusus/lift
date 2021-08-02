package com.acompany.lift.features.routines.detail.data

import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acompany.lift.common.SoundManager
import com.acompany.lift.data.AppRepository
import com.acompany.lift.data.model.Routine
import com.acompany.lift.di.AppModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineDetailViewModel @Inject constructor(
    private val appRepository: AppRepository,
    @AppModule.AppCoroutineScope private val appCoroutineScope: CoroutineScope,
    private val soundManager: SoundManager,
    savedStateHandle: SavedStateHandle,
    private val routineManagerFactory: RoutineManager.RoutineManagerFactory
) : ViewModel() {

    private val routineId = savedStateHandle.get<Long>("routineId")!!

    val screenState = appRepository
        .getRoutines(listOf(routineId))
        .mapNotNull { routines -> routines.firstOrNull() }
        .map { routine -> ScreenState.Ready(routine) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ScreenState.Loading)

    init {
//        if (savedStateHandle.contains(KEY_SESSION_PROGRESS)) {
//            savedStateHandle.get<RoutineProgress>(KEY_SESSION_PROGRESS)?.let {
//                sessionProgress = it
//            }
//        }
//        viewModelScope.launch {
//            val exercises = screenState.filterIsInstance<ScreenState.Ready>().first().routine.exercises
//            exercises.forEach { routineExercise ->
//                savedStateHandle.get<ExerciseProgress>("$KEY_EXERCISE_ID/${routineExercise.id}")?.let { exerciseProgress ->
//                    exerciseProgressMap[routineExercise.id] = exerciseProgress
//                }
//            }
//        }

        // Todo: Restore session & exercise progress from datastore
    }

    fun updateOneRepMax(exerciseId: Long, value: Float?) {
        viewModelScope.launch {
            appRepository.updateExerciseOneRepMax(exerciseId, value)
        }
    }

    fun updateRoutineExerciseWeight(exerciseId: Long, value: Float?) {
        viewModelScope.launch {
            appRepository.updateRoutineExerciseWeight(exerciseId, value)
        }
    }

    fun updateRoutineExercisePercentOneRepMax(exerciseId: Long, value: Float?) {
        viewModelScope.launch {
            appRepository.updateRoutineExercisePercentOneRepMax(exerciseId, value)
        }
    }


    fun playRestTimerTone() {
        soundManager.playSound(SoundManager.Sound.Tone)
    }

    fun updateProgress(routine: Routine) {
        // Use AppCoroutineScope to ensure this occurs regardless of the ViewModel lifecycle
        appCoroutineScope.launch {
            routineManager.advance(routine)
        }
    }

    fun saveSession(routine: Routine) {
        // Use AppCoroutineScope to ensure this occurs regardless of the ViewModel lifecycle
        appCoroutineScope.launch {
            routineManager.save(routine)
        }
    }
}

object PreferenceKeys {
//    val SESSION_PROGRESS = stringSetPreferencesKey("sessionProgress")
//    val EXERCISE_IDS = stringSetPreferencesKey("exerciseIds")
}

