package com.acompany.weightr.features.exercises.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue

import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.acompany.data.AppRepository
import com.acompany.data.model.RoutineExercise
import com.acompany.weightr.features.NavDestination
import com.acompany.weightr.features.exercises.components.ExerciseHelper.initialWeight
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExerciseScreen(paddingValues: PaddingValues, navController: NavController, navBackStackEntry: NavBackStackEntry, repository: AppRepository) {
    val routineExercises by repository.getRoutineExercises(listOf(navBackStackEntry.arguments!!.getLong(NavDestination.ExerciseNavDestination.ARG_ROUTINE_ID)))
        .collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    val editingRoutineExercise = remember { mutableStateOf(null as RoutineExercise?) }
    val textState = remember { mutableStateOf(TextFieldValue(editingRoutineExercise.value?.initialWeight()?.let { weight -> "$weight" } ?: "")) }
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetState = sheetState,
        scrimColor = Color.Transparent,
        sheetContent = {
            Column(
                modifier = Modifier
                    .heightIn(min = 120.dp)
                    .padding(24.dp)
            ) {
                editingRoutineExercise.value?.let { editingRoutineExercise ->
                    Text(text = "Edit ${editingRoutineExercise.exercise.name}")
                    Spacer(modifier = Modifier.size(8.dp))
                    OutlinedTextField(
                        value = textState.value,
                        label = { Text(text = "One Rep Max") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { textFieldValue ->
                            textState.value = textFieldValue
                            scope.launch {
                                repository.updateExercise(editingRoutineExercise.exercise.id, textFieldValue.text.toFloatOrNull())
                            }
                        }
                    )
                }
            }
        }) {
        Column {
            Button(onClick = {}
            ) {
                Text(text = "Start Session")
            }
            Spacer(Modifier.size(8.dp))
            ExerciseList(
                routineExercises = routineExercises,
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp),
                onExerciseClick = { routineExercise ->
                    Timber.d("Click $routineExercise")
                },
                onWeightButtonClick = { routineExercise ->
                    editingRoutineExercise.value = routineExercise
                    scope.launch { sheetState.show() }
                    Timber.d("Long click $routineExercise")
                }
            )
        }
    }
}