package com.acompany.lift.features.routines.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.acompany.lift.common.components.FloatTextField
import com.acompany.lift.common.components.IntTextField
import com.acompany.lift.data.model.Exercise
import com.acompany.lift.data.model.RoutineExercise
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.theme.MaterialColors

@Composable
fun EditRoutineDialog(
    modifier: Modifier = Modifier,
    allExercises: List<Exercise>,
    onDismiss: (RoutineExercise?) -> Unit
) {
    Dialog(
        onDismissRequest = {
            onDismiss(null)
        }
    ) {
        DialogContent(
            modifier = modifier,
            allExercises = allExercises,
            onCancel = { onDismiss(null) },
            onDone = { routineExercise -> onDismiss(routineExercise) }
        )
    }
}

@Composable
private fun DialogContent(
    modifier: Modifier,
    allExercises: List<Exercise>,
    onCancel: () -> Unit,
    onDone: (RoutineExercise) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedExercise by remember { mutableStateOf(allExercises.first()) }

    var weight: Float? by remember { mutableStateOf(null) }
    var sets: Int? by remember { mutableStateOf(3) }
    var reps: Int? by remember { mutableStateOf(5) }

    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colors.surface,
        contentColor = contentColorFor(backgroundColor)
    ) {
        Column(modifier.padding(16.dp)) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                val textStyle = MaterialTheme.typography.subtitle1
                ProvideTextStyle(textStyle) {
                    Text(text = "Add Exercise")
                }
            }
            Spacer(modifier = Modifier.size(24.dp))
            Box(
                modifier
                    .heightIn(min = 48.dp)
                    .background(MaterialColors.onBackground.copy(alpha = 0.05f))
                    .fillMaxWidth()
                    .clickable(onClick = { expanded = true })
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = modifier,
                        text = selectedExercise.name,
                    )
                    Icon(
                        imageVector = Icons.Rounded.ArrowDropDown,
                        contentDescription = "More exercises",
                        tint = MaterialTheme.colors.onSurface
                    )
                }
                DropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp),
                    expanded = expanded,
                    onDismissRequest = { expanded = false }) {
                    allExercises.forEach { exercise ->
                        DropdownMenuItem(onClick = {
                            selectedExercise = exercise
                            expanded = false
                        }) {
                            Text(text = exercise.name)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            FloatTextField(
                modifier = modifier.fillMaxWidth(),
                key = "weight",
                label = "Weight",
                initialValue = weight,
                onValueChanged = { weight = it })
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IntTextField(
                    modifier = modifier.weight(1f),
                    key = "sets",
                    label = "Sets",
                    initialValue = sets,
                    onValueChanged = { sets = it }) {
                }
                Spacer(modifier = Modifier.size(16.dp))
                IntTextField(
                    modifier = modifier.weight(1f),
                    key = "reps",
                    label = "Reps",
                    initialValue = reps,
                    onValueChanged = { reps = it }
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    OutlinedButton(onClick = { onCancel() }) {
                        Text(text = "Cancel")
                    }
                    Spacer(Modifier.size(16.dp))
                    OutlinedButton(
                        onClick = {
                            onDone(
                                RoutineExercise(
                                    id = 0,
                                    order = 0,
                                    sets = sets!!,
                                    reps = reps!!,
                                    percentOneRepMax = null,
                                    weight = weight,
                                    routineId = 0,
                                    exercise = selectedExercise,
                                    restPeriod = 90
                                )
                            )
                        },
                        enabled = sets != null && reps != null
                    ) {
                        Text(text = "Done")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ExerciseListDialogContentPreview() {
    DialogContent(
        modifier = Modifier.background(color = Color.White),
        allExercises = DummyAppRepository.exercises,
        onCancel = {},
        onDone = {}
    )
}