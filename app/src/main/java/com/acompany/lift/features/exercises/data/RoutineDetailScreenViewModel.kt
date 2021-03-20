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
import com.acompany.lift.data.model.RoutineExercise
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

    val exerciseProgressMap = mutableStateMapOf<RoutineExercise, ExerciseProgress>()

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
            exercises.forEach { exercise ->
                savedStateHandle.get<ExerciseProgress>("$KEY_EXERCISE_ID/${exercise.id}")?.let { exerciseProgress ->
                    exerciseProgressMap[exercise] = exerciseProgress
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
                        ),
                        routine = routine
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
                routine.exercises.forEachIndexed { index, exercise ->
                    if (index == 0) {
                        exerciseProgressMap[exercise] = ExerciseProgress.InProgress(0)
                    } else {
                        exerciseProgressMap[exercise] = ExerciseProgress.None
                    }
                }
                sessionProgress = RoutineProgress.InProgress(Date(), routine.exercises.first())
            }
            is RoutineProgress.InProgress -> {
                when (val currentExerciseProgress = exerciseProgressMap[_sessionProgress.currentExercise]!!) {
                    is ExerciseProgress.None -> {
                        exerciseProgressMap[_sessionProgress.currentExercise] = ExerciseProgress.InProgress(0)
                    }
                    is ExerciseProgress.InProgress -> {
                        exerciseProgressMap[_sessionProgress.currentExercise] = ExerciseProgress.Resting(currentExerciseProgress.set)
                    }
                    is ExerciseProgress.Resting -> {
                        if (currentExerciseProgress.set < _sessionProgress.currentExercise.sets - 1) {
                            exerciseProgressMap[_sessionProgress.currentExercise] = ExerciseProgress.InProgress(currentExerciseProgress.set + 1)
                        } else {
                            exerciseProgressMap[_sessionProgress.currentExercise] = ExerciseProgress.Complete
                            val index = routine.exercises.indexOf(_sessionProgress.currentExercise)
                            if (index < exerciseProgressMap.size - 1) {
                                val nextExercise = routine.exercises[index + 1]
                                exerciseProgressMap[nextExercise] = ExerciseProgress.InProgress(0)
                                sessionProgress = RoutineProgress.InProgress(
                                    startDate = _sessionProgress.startDate,
                                    currentExercise = nextExercise
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

        exerciseProgressMap.keys.forEach { routineExercise ->
            val progress = exerciseProgressMap[routineExercise]
            savedStateHandle.set("$KEY_EXERCISE_ID/${routineExercise.id}", progress)
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