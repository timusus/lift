package com.acompany.lift.features.exercises.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acompany.lift.data.AppRepository
import com.acompany.lift.data.model.Routine
import com.acompany.lift.data.model.RoutineExercise
import com.acompany.lift.features.exercises.data.ExerciseHelper.initialWeight
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun ExerciseScreen(
    routineId: Long,
    repository: AppRepository
) {
    val scope = rememberCoroutineScope()
    val routineExercises by repository.getRoutineExercises(listOf(routineId)).collectAsState(emptyList())
    val routine by repository.getRoutines(listOf(routineId)).map { it.first() }.collectAsState(initial = null)
    var selectedExercise by remember { mutableStateOf(null as RoutineExercise?) }
    routine?.let { routine ->
        ExerciseModalSheet(
            sheetContent = {
                selectedExercise?.let { selectedExercise ->
                    ExerciseWeightTextField(
                        routine = routine,
                        routineExercise = selectedExercise,
                        onOneRepMaxChanged = { weight ->
                            selectedExercise.let { routineExercise ->
                                scope.launch {
                                    repository.updateExercise(routineExercise.exercise.id, weight)
                                }
                            }
                        },
                        onDoneClick = {
                            closeDrawer()
                        }
                    )
                }
            },
            content = {
                ExerciseList(
                    routineExercises = routineExercises,
                    onExerciseClick = { routineExercise ->
                        selectedExercise = routineExercise
                        openDrawer()
                    }
                )
            }
        )
    }
}

@Composable
private fun ExerciseWeightTextField(
    routine: Routine,
    routineExercise: RoutineExercise,
    onOneRepMaxChanged: (Float?) -> Unit,
    onDoneClick: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(modifier = Modifier.weight(1f), text = "Edit ${routineExercise.exercise.name}")
        OutlinedButton(onClick = onDoneClick) {
            Text(text = "Done")
        }
    }
    Spacer(modifier = Modifier.size(16.dp))
    Text(fontSize = 12.sp, text = "All ${routineExercise.exercise.name}(s)")
    Spacer(modifier = Modifier.size(4.dp))
    FloatTextField(
        key = routineExercise.id.toString(),
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

        }
    )
    Spacer(modifier = Modifier.size(8.dp))
    FloatTextField(
        key = routineExercise.id.toString(),
        label = "Weight",
        initialValue = routineExercise.initialWeight(),
        onValueChanged = { value ->

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
