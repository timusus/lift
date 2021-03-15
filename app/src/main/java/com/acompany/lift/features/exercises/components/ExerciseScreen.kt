package com.acompany.lift.features.exercises.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acompany.lift.data.model.Routine
import com.acompany.lift.data.model.RoutineExercise
import com.acompany.lift.features.exercises.data.ExerciseHelper.initialWeight

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExerciseScreen(
    viewModel: ExerciseScreenViewModel,
    routineId: Long
) {
    val routine by viewModel.getRoutine(routineId).collectAsState(initial = null)
    val selectedExercise by viewModel.selectedRoutineExercise.collectAsState()
    val sessionState by viewModel.sessionState.collectAsState()

    routine?.let { routine ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = routine.name)
                    },
                    actions = {
                        ExerciseMenuItem(sessionState.startDate, onStartClick = {
                            viewModel.moveToNextState(routine.exercises)
                        })
                    }
                )
            },
            content = {
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
                            sessionState = sessionState,
                            onExerciseClick = { routineExercise ->
                                viewModel.setSelectedRoutineExercise(routineExercise)
                                show()
                            },
                            onActionClick = { routineExercise ->
                                viewModel.moveToNextState(routine.exercises)
                            }
                        )
                    }
                )
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SheetContent(
    routine: Routine,
    routineExercise: RoutineExercise,
    onOneRepMaxChanged: (Float?) -> Unit,
    onWeightChanged: (Float?) -> Unit,
    onPercentOneRepMaxChanged: (Float?) -> Unit,
    onDoneClick: () -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(modifier = Modifier.weight(1f), text = "Edit ${routineExercise.exercise.name}")
        OutlinedButton(onClick = {
            keyboardController?.hideSoftwareKeyboard()
            onDoneClick()
        }) {
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
        onValueChanged = { value -> onOneRepMaxChanged(value) },
        onDone = { keyboardController?.hideSoftwareKeyboard() }
    )
    Spacer(modifier = Modifier.size(24.dp))
    Text(fontSize = 12.sp, text = "Routine:  ${routine.name}")
    Spacer(modifier = Modifier.size(4.dp))
    FloatTextField(
        key = routineExercise.id.toString(),
        label = "% One rep max",
        initialValue = routineExercise.percentOneRepMax?.let { (it * 100) },
        onValueChanged = { value -> onPercentOneRepMaxChanged(value?.let { it / 100f }) },
        onDone = { keyboardController?.hideSoftwareKeyboard() }
    )
    Spacer(modifier = Modifier.size(8.dp))
    FloatTextField(
        key = routineExercise.id.toString(),
        label = "Weight",
        initialValue = routineExercise.weight ?: routineExercise.initialWeight(),
        onValueChanged = { value -> onWeightChanged(value) },
        onDone = { keyboardController?.hideSoftwareKeyboard() }
    )
}

@Composable
private fun FloatTextField(
    key: String,
    label: String,
    initialValue: Float?,
    onValueChanged: (Float?) -> Unit,
    onDone: () -> Unit
) {
    val textState = remember(key) { mutableStateOf(initialValue?.toString() ?: "") }
    OutlinedTextField(
        value = textState.value,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
        onValueChange = { textFieldValue ->
            textState.value = textFieldValue
            onValueChanged(textFieldValue.toFloatOrNull())
        },
        keyboardActions = KeyboardActions(onDone = { onDone() })
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
    sessionState: ExerciseScreenViewModel.SessionState,
    onExerciseClick: (RoutineExercise) -> Unit,
    onActionClick: (RoutineExercise) -> Unit
) {
    ExerciseList(
        routineExercises = routineExercises,
        sessionState = sessionState,
        modifier = Modifier.padding(8.dp),
        onExerciseClick = { routineExercise ->
            onExerciseClick(routineExercise)
        },
        onActionClick = { routineExercise ->
            onActionClick(routineExercise)
        }
    )
}
