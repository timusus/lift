package com.acompany.lift.features.exercises.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acompany.lift.data.AppRepository
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
    val routineIds = listOf(routineId)
    val routineExercises by repository.getRoutineExercises(routineIds).collectAsState(emptyList())
    val routine by repository.getRoutines(routineIds).map { it.first() }.collectAsState(null)

    val scope = rememberCoroutineScope()
    var selectedExercise by remember { mutableStateOf(null as RoutineExercise?) }
    val updateExercise: (suspend (RoutineExercise) -> Unit) -> Unit = { block ->
        selectedExercise?.let { routineExercise ->
            scope.launch { block(routineExercise) }
        }
    }
    ExerciseModalSheet(
        sheetContent = {
            DoneButton(text = "Edit ${selectedExercise?.exercise?.name}")
            ExerciseWeightForm(
                routineName = routine?.name,
                routineExercise = selectedExercise,
                onOneRepMaxChanged = { oneRepMax ->
                    updateExercise { routineExercise ->
                        repository.updateExercise(routineExercise.exercise.id, oneRepMax)
                    }
                },
                onPercentOneRepMaxChanged = { percentRep ->
                    Timber.d("$percentRep")
                },
                onWeightChanged = { weight ->
                    Timber.d("$weight")
                }
            )
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

@Composable
private fun ExerciseWeightForm(
    routineName: String?,
    routineExercise: RoutineExercise?,
    onOneRepMaxChanged: (Float?) -> Unit,
    onPercentOneRepMaxChanged: (Float?) -> Unit,
    onWeightChanged: (Float?) -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))
    FloatTextField(
        label = "One rep max",
        description = "All ${routineExercise?.exercise?.name}(s)",
        initialValue = routineExercise?.exercise?.oneRepMax,
        onValueChanged = onOneRepMaxChanged
    )
    Spacer(modifier = Modifier.height(24.dp))
    FloatTextField(
        label = "% One rep max",
        description = "Routine:  $routineName",
        initialValue = routineExercise?.percentOneRepMax?.let { (it * 100) },
        onValueChanged = onPercentOneRepMaxChanged
    )
    Spacer(modifier = Modifier.height(8.dp))
    FloatTextField(
        label = "Weight",
        initialValue = routineExercise?.initialWeight(),
        onValueChanged = onWeightChanged
    )
}

@Composable
private fun ModalSheetScope.DoneButton(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.weight(1f),
            text = text
        )
        OutlinedButton(onClick = { closeDrawer() }) {
            Text(text = "Done")
        }
    }
}

@Composable
@OptIn(ExperimentalComposeUiApi::class)
private fun FloatTextField(
    label: String,
    initialValue: Float?,
    description: String? = null,
    onValueChanged: (Float?) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val textState = remember(label, initialValue, description) {
        mutableStateOf(initialValue?.toString() ?: "")
    }
    val onValueChangedState by rememberUpdatedState(onValueChanged)
    description?.let { desc ->
        Text(fontSize = 12.sp, text = desc)
        Spacer(modifier = Modifier.size(4.dp))
    }
    OutlinedTextField(
        singleLine = true,
        value = textState.value,
        label = { Text(text = label) },
        keyboardActions = KeyboardActions {
            keyboardController?.hideSoftwareKeyboard()
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = { text ->
            textState.value = text
            onValueChangedState(text.toFloatOrNull())
        }
    )
}