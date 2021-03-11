package com.acompany.weightr.features.exercises.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.acompany.data.AppRepository
import com.acompany.data.model.RoutineExercise
import com.acompany.weightr.features.exercises.components.ExerciseHelper.initialWeight
import timber.log.Timber

@Composable
fun ExerciseScreen(
    routineId: Long,
    repository: AppRepository
) {
    val routineExercises by repository.getRoutineExercises(listOf(routineId)).collectAsState(emptyList())
    var editingRoutineExercise by remember { mutableStateOf(null as RoutineExercise?) }
    ExerciseModelSheet(
        sheetContent = {
            ExerciseWeightTextField(editingRoutineExercise?.initialWeight())
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
private fun ExerciseWeightTextField(initialWeight: Float?) {
    val textState = remember { mutableStateOf(initialWeight?.toString() ?: "") }
    OutlinedTextField(
        value = textState.value,
        onValueChange = { textState.value = it }
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
    LazyExerciseList(
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
