package com.acompany.lift.features.exercises.components

import android.text.format.DateUtils
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.DoubleArrow
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acompany.lift.common.components.FloatTextField
import com.acompany.lift.common.components.elapsedTimeMillis
import com.acompany.lift.data.model.Routine
import com.acompany.lift.data.model.RoutineExercise
import com.acompany.lift.features.exercises.data.ExerciseHelper.initialWeight
import com.acompany.lift.features.exercises.data.ExerciseScreenPreviewProvider
import com.acompany.lift.features.main.data.DummyAppRepository
import java.util.*

@Composable
@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
fun ExerciseScreen(
    viewModel: ExerciseScreenViewModel,
    routineId: Long
) {
    val routine by viewModel.getRoutine(routineId).collectAsState(initial = null)
    routine?.let { routine ->
        ExerciseScreen(viewModel = viewModel, routine = routine)
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
fun ExerciseScreen(
    viewModel: ExerciseScreenViewModel,
    routine: Routine
) {
    val selectedExercise by viewModel.selectedRoutineExercise.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = routine.name) })
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
                            onDoneClick = { hide() }
                        )
                    }
                },
                content = {
                    ExerciseList(
                        routineExercises = routine.exercises,
                        exerciseProgress = viewModel.exerciseProgressMap,
                        onExerciseClick = { routineExercise ->
                            viewModel.setSelectedRoutineExercise(routineExercise)
                            show()
                        },
                        onActionClick = { routineExercise -> viewModel.moveToNext(routine) }
                    )
                    SessionProgressFloatingActionButton(viewModel.sessionProgress) {
                        viewModel.moveToNext(routine)
                    }
                }
            )
        }
    )
}

@Composable
@OptIn(ExperimentalComposeUiApi::class)
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

@Preview
@Composable
private fun ExerciseScreenPreview(
    @PreviewParameter(ExerciseScreenPreviewProvider::class) preview: Pair<Colors, ExerciseScreenViewModel>
) {
    MaterialTheme(colors = preview.first) {
        ExerciseScreen(preview.second, DummyAppRepository.routines.first())
    }
}

@Composable
fun SessionProgressFloatingActionButton(
    sessionProgress: ExerciseScreenViewModel.SessionProgress,
    onClick: () -> Unit
) {
    val (sessionIcon, sessionIconDescription) = when (sessionProgress) {
        ExerciseScreenViewModel.SessionProgress.None -> Icons.Rounded.Timer to "start session"
        is ExerciseScreenViewModel.SessionProgress.InProgress -> Icons.Rounded.DoubleArrow to "next session"
        is ExerciseScreenViewModel.SessionProgress.Complete -> Icons.Rounded.CheckCircle to "completed"
    }
    AnimatedFloatingActionButton(
        text = DateUtils.formatElapsedTime(
            elapsedTimeMillis(sessionProgress.startDate ?: Date())
        ),
        icon = sessionIcon,
        contentDescription = sessionIconDescription,
        expanded = sessionProgress is ExerciseScreenViewModel.SessionProgress.InProgress,
        onClick = onClick,
        modifier = Modifier.bottomEndFabPlacement()
    )
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun AnimatedFloatingActionButton(
    text: String,
    icon: ImageVector,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AnimatedVisibility(visible = expanded) {
                Box(Modifier.padding(start = 16.dp, end = 8.dp)) {
                    Text(text = text)
                }
            }
            Icon(
                imageVector = icon,
                contentDescription = contentDescription
            )
            AnimatedVisibility(visible = expanded) {
                Spacer(Modifier.width(20.dp))
            }
        }
    }
}

fun Modifier.bottomEndFabPlacement(): Modifier {
    return this.then(
        fillMaxSize()
            .wrapContentSize(Alignment.BottomEnd)
            .padding(end = 16.dp, bottom = 16.dp)
    )
}