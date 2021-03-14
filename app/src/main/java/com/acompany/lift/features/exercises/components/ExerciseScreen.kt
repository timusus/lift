package com.acompany.lift.features.exercises.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.acompany.lift.data.AppRepository
import com.acompany.lift.data.model.Routine
import com.acompany.lift.data.model.RoutineExercise
import com.acompany.lift.features.exercises.data.ExerciseHelper.initialWeight
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExerciseScreenViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    fun getRoutine(routineId: Long): Flow<Routine?> {
        return appRepository.getRoutines(listOf(routineId)).map { routines -> routines.firstOrNull() }
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

    val startDate: MutableStateFlow<Date?> = MutableStateFlow(null)
    fun setStartDate(date: Date) {
        viewModelScope.launch {
            startDate.emit(date)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExerciseScreen(
    viewModel: ExerciseScreenViewModel,
    routineId: Long
) {
    val routine by viewModel.getRoutine(routineId).collectAsState(null)
    val selectedExercise by viewModel.selectedRoutineExercise.collectAsState()
    val startDate by viewModel.startDate.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    routine?.let { routine ->
                        Text(text = routine.name)
                    }
                },
                actions = {
                    ExerciseMenuItem(startDate, onStartClick = {
                        viewModel.setStartDate(Date())
                    })
                }
            )
        },
        content = {
            routine?.let { routine ->
                ExerciseModalSheet(
                    sheetContent = {
                        selectedExercise?.let { selectedExercise ->
                            SheetContent(
                                routine = routine,
                                routineExercise = selectedExercise,
                                onOneRepMaxChanged = { oneRepMax -> viewModel.updateOneRepMax(selectedExercise.id, oneRepMax) },
                                onWeightChanged = { weight -> viewModel.updateRoutineExerciseWeight(selectedExercise.id, weight) },
                                onPercentOneRepMaxChanged = { percentOneRepMax -> viewModel.updateRoutineExercisePercentOneRepMax(selectedExercise.id, percentOneRepMax) },
                                onDoneClick = {
                                    hide()
                                }
                            )
                        }
                    },
                    content = {
                        ExerciseList(
                            routineExercises = routine.exercises,
                            onExerciseClick = { routineExercise ->
                                viewModel.setSelectedRoutineExercise(routineExercise)
                                show()
                            }
                        )
                    }
                )
            }
        }
    )
}

@Composable
private fun SheetContent(
    routine: Routine,
    routineExercise: RoutineExercise,
    onOneRepMaxChanged: (Float?) -> Unit,
    onWeightChanged: (Float?) -> Unit,
    onPercentOneRepMaxChanged: (Float?) -> Unit,
    onDoneClick: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(modifier = Modifier.weight(1f), text = "Edit ${routineExercise?.exercise?.name}")
        OutlinedButton(onClick = onDoneClick) {
            Text(text = "Done")
        }
    }
    Spacer(modifier = Modifier.size(16.dp))
    Text(fontSize = 12.sp, text = "All ${routineExercise.exercise.name}(s)")
    Spacer(modifier = Modifier.size(4.dp))
    FloatTextField(
        key = routineExercise?.id.toString(),
        label = "One rep max",
        initialValue = routineExercise.exercise.oneRepMax,
        onValueChanged = { value ->
            onOneRepMaxChanged(value)
        }
    )
    Spacer(modifier = Modifier.size(24.dp))
    Text(fontSize = 12.sp, text = "Routine:  ${routine.name}")
    Spacer(modifier = Modifier.size(4.dp))
    FloatTextField(
        key = routineExercise.id.toString(),
        label = "% One rep max",
        initialValue = routineExercise.percentOneRepMax?.let { (it * 100) },
        onValueChanged = { value ->
            onPercentOneRepMaxChanged(value?.let { it / 100f })
        }
    )
    Spacer(modifier = Modifier.size(8.dp))
    FloatTextField(
        key = routineExercise.id.toString(),
        label = "Weight",
        initialValue = routineExercise.weight ?: routineExercise.initialWeight(),
        onValueChanged = { value ->
            onWeightChanged(value)
        }
    )

}

@Composable
private fun FloatTextField(
    key: String,
    label: String,
    initialValue: Float?,
    onValueChanged: (Float?) -> Unit,
) {
    val textState = remember(key) { mutableStateOf(initialValue?.toString() ?: "") }
    OutlinedTextField(
        value = textState.value,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = { textFieldValue ->
            textState.value = textFieldValue
            onValueChanged(textFieldValue.toFloatOrNull())
        }
    )
}

@Composable
private fun StartSessionButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Start Session")
    }
}

@Composable
private fun ExerciseList(
    routineExercises: List<RoutineExercise>,
    onExerciseClick: (RoutineExercise) -> Unit = {}
) {
    ExerciseList(
        routineExercises = routineExercises,
        modifier = Modifier.padding(8.dp),
        onExerciseClick = { routineExercise ->
            onExerciseClick(routineExercise)
            Timber.d("Click $routineExercise")
        }
    )
}
