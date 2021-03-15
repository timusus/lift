package com.acompany.lift.features.exercises.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acompany.lift.data.AppRepository
import com.acompany.lift.data.model.Routine
import com.acompany.lift.data.model.RoutineExercise
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExerciseScreenViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    fun getRoutine(routineId: Long): Flow<Routine> {
        return appRepository.getRoutines(listOf(routineId)).map { list -> list.first() }
    }

    val selectedRoutineExercise: MutableStateFlow<RoutineExercise?> = MutableStateFlow(null)

    fun setSelectedRoutineExercise(exercise: RoutineExercise) {
        viewModelScope.launch {
            selectedRoutineExercise.emit(exercise)
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

    sealed class SessionState(val startDate: Date?) {
        object None : SessionState(null)
        class InProgress(startDate: Date, val exercise: RoutineExercise, val set: Int) : SessionState(startDate)
        class Resting(startDate: Date, val exercise: RoutineExercise, val set: Int) : SessionState(startDate)
        class Complete(startDate: Date) : SessionState(startDate)
    }

    val sessionState = MutableStateFlow<SessionState>(SessionState.None)

    fun moveToNextState(exercises: List<RoutineExercise>) {
        when (val state = sessionState.value) {
            is SessionState.None -> {
                viewModelScope.launch {
                    sessionState.emit(SessionState.InProgress(Date(), exercises.first(), 0))
                }
            }
            is SessionState.InProgress -> {
                viewModelScope.launch {
                    sessionState.emit(
                        SessionState.Resting(state.startDate!!, state.exercise, state.set)
                    )
                }
            }
            is SessionState.Resting -> {
                viewModelScope.launch {
                    val index = exercises.indexOf(state.exercise)
                    sessionState.emit(
                        if (state.set <= state.exercise.sets - 2) {
                            SessionState.InProgress(state.startDate!!, state.exercise, state.set + 1)
                        } else if (index <= exercises.size - 2) {
                            SessionState.InProgress(state.startDate!!, exercises[index + 1], 0)
                        } else {
                            SessionState.Complete(state.startDate!!)
                        }
                    )
                }
            }
            is SessionState.Complete -> {
                // No op
            }
        }
    }
}