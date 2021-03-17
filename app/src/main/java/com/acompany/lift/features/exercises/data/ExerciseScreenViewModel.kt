package com.acompany.lift.features.exercises.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class ExerciseScreenViewModel @Inject constructor(
    private val appRepository: AppRepository,
    @AppModule.AppCoroutineScope private val appScope: CoroutineScope
) : ViewModel() {

    fun getRoutine(id: Long): Flow<Routine> {
        return appRepository.getRoutines(listOf(id)).map { routines -> routines.first() }
    }

    private val _selectedRoutineExercise: MutableStateFlow<RoutineExercise?> = MutableStateFlow(null)
    val selectedRoutineExercise: StateFlow<RoutineExercise?> = _selectedRoutineExercise.asStateFlow()

    fun setSelectedRoutineExercise(exercise: RoutineExercise) {
        _selectedRoutineExercise.tryEmit(exercise)
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
        if (sessionProgress is SessionProgress.Complete) {
            appScope.launch { // We use app scope here, as the ViewModel may go out of scope during a save
                appRepository.createSession(
                    session = Session(
                        id = 0,
                        startDate = sessionProgress.startDate!!,
                        endDate = Date(),
                        routineId = routine.id,
                        exercises = routine.exercises.map { routineExercise ->
                            routineExercise.toSessionExercise()
                        }
                    ),
                    routine = routine
                )
            }
        } else {
            Timber.e("Cannot save incomplete session")
        }
    }

    sealed class ExerciseProgress {
        object None : ExerciseProgress()
        class InProgress(val set: Int) : ExerciseProgress()
        class Resting(val set: Int) : ExerciseProgress()
        object Complete : ExerciseProgress()
    }

    val exerciseProgressMap = mutableStateMapOf<RoutineExercise, ExerciseProgress>()

    sealed class SessionProgress(val startDate: Date?) {
        object None : SessionProgress(null)
        class InProgress(startDate: Date?, val currentExercise: RoutineExercise) : SessionProgress(startDate)
        class Complete(startDate: Date?, val shouldSave: Boolean) : SessionProgress(startDate)
    }

    var sessionProgress by mutableStateOf<SessionProgress>(SessionProgress.None)

    fun updateProgress(routine: Routine) {
        when (val _sessionProgress = sessionProgress) {
            is SessionProgress.None -> {
                routine.exercises.forEachIndexed { index, exercise ->
                    if (index == 0) {
                        exerciseProgressMap[exercise] = ExerciseProgress.InProgress(0)
                    } else {
                        exerciseProgressMap[exercise] = ExerciseProgress.None
                    }
                }
                sessionProgress = SessionProgress.InProgress(Date(), routine.exercises.first())
            }
            is SessionProgress.InProgress -> {
                when (val currentExerciseProgress =
                    exerciseProgressMap[_sessionProgress.currentExercise]!!) {
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
                                sessionProgress = SessionProgress.InProgress(
                                    startDate = _sessionProgress.startDate,
                                    currentExercise = nextExercise
                                )
                            } else {
                                sessionProgress = SessionProgress.Complete(
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
            is SessionProgress.Complete -> {
                if (!_sessionProgress.shouldSave) {
                    sessionProgress = SessionProgress.Complete(
                        startDate = _sessionProgress.startDate,
                        shouldSave = true
                    )
                }
            }
        }
    }
}