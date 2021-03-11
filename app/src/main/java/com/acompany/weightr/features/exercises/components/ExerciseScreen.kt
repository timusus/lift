package com.acompany.weightr.features.exercises.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.acompany.data.AppRepository
import com.acompany.data.model.RoutineExercise
import com.acompany.weightr.features.exercises.components.ExerciseHelper.initialWeight
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun ExerciseScreen(
    routineId: Long,
    repository: AppRepository
) {
    val scope = rememberCoroutineScope()
    val routineExercises by repository.getRoutineExercises(listOf(routineId)).collectAsState(emptyList())
    var editingRoutineExercise by remember { mutableStateOf(null as RoutineExercise?) }
    ExerciseModelSheet(
        sheetContent = {
            editingRoutineExercise?.let { routine ->
                ExerciseWeightTextField(
                    exerciseName = routine.exercise.name,
                    initialWeight = editingRoutineExercise?.initialWeight(),
                    onWeightChanged = { weight ->
                        scope.launch {
                            repository.updateExercise(routine.exercise.id, weight)
                        }
                    }
                )
            }
        },
        content = {
            StartSessionButton {

            }
            ExerciseList(
                routineExercises = routineExercises,
                onWeightButtonClick = { routineExercise ->
                    editingRoutineExercise = routineExercise
                    openDrawer()
                }
            )
        }
    )
}

@Composable
private fun ExerciseWeightTextField(
    exerciseName: String,
    initialWeight: Float?,
    onWeightChanged: (Float?) -> Unit
) {
    val textState = remember { mutableStateOf(initialWeight?.toString() ?: "") }
    Text(text = "Edit $exerciseName")
    OutlinedTextField(
        value = textState.value,
        label = { Text(text = "One Rep Max") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = { textFieldValue ->
            textState.value = textFieldValue
            onWeightChanged(textFieldValue.toFloatOrNull())
        }
    )
}

@Composable
private fun StartSessionButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = "Start Session")
    }
}

@Composable
private fun ExerciseList(
    routineExercises: List<RoutineExercise>,
    onExerciseClick: (RoutineExercise) -> Unit = {},
    onWeightButtonClick: (RoutineExercise) -> Unit = {}
) {
    ExerciseList(
        routineExercises = routineExercises,
        modifier = Modifier.padding(16.dp),
        onExerciseClick = { routineExercise ->
            onExerciseClick(routineExercise)
            Timber.d("Click $routineExercise")
        },
        onWeightButtonClick = { routineExercise ->
            onWeightButtonClick(routineExercise)
            Timber.d("Long click $routineExercise")
        }
    )
}
