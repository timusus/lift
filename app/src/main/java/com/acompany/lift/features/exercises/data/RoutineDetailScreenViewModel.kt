package com.acompany.lift.features.exercises.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acompany.lift.common.SoundManager
import com.acompany.lift.data.AppRepository
import com.acompany.lift.data.model.Mapper.toSessionExercise
import com.acompany.lift.data.model.Routine
import com.acompany.lift.data.model.Session
import com.acompany.lift.di.AppModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RoutineDetailScreenViewModel @Inject constructor(
    private val appRepository: AppRepository,
    @AppModule.AppCoroutineScope private val appCoroutineScope: CoroutineScope,
    private val soundManager: SoundManager,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val routineId = savedStateHandle.get<Long>("routineId")!!

    val exerciseProgressMap = mutableStateMapOf<Long, ExerciseProgress>()

    var sessionProgress by mutableStateOf<RoutineProgress>(RoutineProgress.None)

    val screenState = appRepository
        .getRoutines(listOf(routineId))
        .mapNotNull { routines -> routines.firstOrNull() }
        .map { routine -> ScreenState.Ready(routine) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ScreenState.Loading)

    init {
        if (savedStateHandle.contains(KEY_SESSION_PROGRESS)) {
            savedStateHandle.get<RoutineProgress>(KEY_SESSION_PROGRESS)?.let {
                sessionProgress = it
            }
        }
        viewModelScope.launch {
            val exercises = screenState.filterIsInstance<ScreenState.Ready>().first().routine.exercises
            exercises.forEach { routineExercise ->
                savedStateHandle.get<ExerciseProgress>("$KEY_EXERCISE_ID/${routineExercise.id}")?.let { exerciseProgress ->
                    exerciseProgressMap[routineExercise.id] = exerciseProgress
                }
            }
        }
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

    fun saveSession(routine: Routine) {
        when (val _sessionProgress = sessionProgress) {
            is RoutineProgress.Complete -> {
                appCoroutineScope.launch { // We use app scope here, as the ViewModel may go out of scope during a save
                    appRepository.createSession(
                        session = Session(
                            id = 0,
                            startDate = _sessionProgress.startDate,
                            endDate = Date(),
                            routine = routine,
                            exercises = routine.exercises.map { routineExercise ->
                                routineExercise.toSessionExercise()
                            }
                        )
                    )
                }
            }
            else -> {
                Timber.e("Cannot save incomplete session")
            }
        }
    }

    fun updateProgress(routine: Routine) {
        when (val _sessionProgress = sessionProgress) {
            is RoutineProgress.None -> {
                routine.exercises.forEachIndexed { index, routineExercise ->
                    if (index == 0) {
                        exerciseProgressMap[routineExercise.id] = ExerciseProgress.InProgress(0)
                    } else {
                        exerciseProgressMap[routineExercise.id] = ExerciseProgress.None
                    }
                }
                sessionProgress = RoutineProgress.InProgress(Date(), routine.exercises.first().id)
            }
            is RoutineProgress.InProgress -> {
                val currentRoutineExerciseId = _sessionProgress.currentRoutineExerciseId
                val currentRoutineExercise = routine.exercises.first { it.id == currentRoutineExerciseId }
                when (val currentExerciseProgress = exerciseProgressMap[currentRoutineExerciseId]!!) {
                    is ExerciseProgress.None -> {
                        exerciseProgressMap[currentRoutineExerciseId] = ExerciseProgress.InProgress(0)
                    }
                    is ExerciseProgress.InProgress -> {
                        exerciseProgressMap[currentRoutineExerciseId] = ExerciseProgress.Resting(currentExerciseProgress.set)
                    }
                    is ExerciseProgress.Resting -> {
                        if (currentExerciseProgress.set < currentRoutineExercise.sets - 1) {
                            exerciseProgressMap[currentRoutineExerciseId] = ExerciseProgress.InProgress(currentExerciseProgress.set + 1)
                        } else {
                            exerciseProgressMap[currentRoutineExerciseId] = ExerciseProgress.Complete
                            val index = routine.exercises.indexOf(currentRoutineExercise)
                            if (index < exerciseProgressMap.size - 1) {
                                val nextExerciseId = routine.exercises[index + 1].id
                                exerciseProgressMap[nextExerciseId] = ExerciseProgress.InProgress(0)
                                sessionProgress = RoutineProgress.InProgress(
                                    startDate = _sessionProgress.startDate,
                                    currentRoutineExerciseId = nextExerciseId
                                )
                            } else {
                                sessionProgress = RoutineProgress.Complete(
                                    startDate = _sessionProgress.startDate,
                                    shouldSave = false
                                )
                            }
                        }
                    }
                    is ExerciseProgress.Complete -> {
                    }
                }
            }
            is RoutineProgress.Complete -> {
                if (!_sessionProgress.shouldSave) {
                    sessionProgress = RoutineProgress.Complete(
                        startDate = _sessionProgress.startDate,
                        shouldSave = true
                    )
                }
            }
        }

        exerciseProgressMap.keys.forEach { routineExerciseId ->
            val progress = exerciseProgressMap[routineExerciseId]
            savedStateHandle.set("$KEY_EXERCISE_ID/${routineExerciseId}", progress)
        }
        savedStateHandle.set(KEY_SESSION_PROGRESS, sessionProgress)
    }

    fun playRestTimerTone() {
        soundManager.playSound(SoundManager.Sound.Tone)
    }

    companion object {
        const val KEY_SESSION_PROGRESS = "session_progress"
        const val KEY_EXERCISE_ID = "exercise_id/"
    }
}