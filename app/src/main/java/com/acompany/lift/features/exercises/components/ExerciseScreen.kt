package com.acompany.lift.features.exercises.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.acompany.lift.data.model.Routine
import com.acompany.lift.data.model.RoutineExercise
import com.acompany.lift.features.exercises.data.ExerciseScreenPreviewProvider
import com.acompany.lift.features.exercises.data.ExerciseScreenViewModel
import com.acompany.lift.features.main.data.DummyAppRepository
import java.util.*

@Composable
@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
fun ExerciseScreen(
    modifier: Modifier = Modifier,
    viewModel: ExerciseScreenViewModel,
    routine: Routine,
    onSessionComplete: () -> Unit,
) {

    val routine: Routine by viewModel.getRoutine(routine.id).collectAsState(initial = routine)
    var selectedExercise by mutableStateOf<RoutineExercise?>(null)

    ExerciseScreen(
        modifier = modifier,
        routine = routine,
        selectedExercise = selectedExercise,
        sessionProgress = viewModel.sessionProgress,
        exerciseProgressMap = viewModel.exerciseProgressMap,
        onSessionComplete = onSessionComplete,
        onExerciseSelected = { routineExercise ->
            selectedExercise = routineExercise
        },
        onOneRepMaxChanged = { routineExercise, oneRepMax ->
            viewModel.updateOneRepMax(
                exerciseId = routineExercise.id,
                value = oneRepMax
            )
        },
        onWeightChanged = { routineExercise, weight ->
            viewModel.updateRoutineExerciseWeight(
                exerciseId = routineExercise.id,
                value = weight
            )
        },
        onPercentOneRepMaxChanged = { routineExercise, percentOneRepMax ->
            viewModel.updateRoutineExercisePercentOneRepMax(
                exerciseId = routineExercise.id,
                value = percentOneRepMax
            )
        },
        onUpdateExerciseProgress = {
            viewModel.updateProgress(routine)
            when (val sessionProgress = viewModel.sessionProgress) {
                is ExerciseScreenViewModel.SessionProgress.Complete -> {
                    if (sessionProgress.shouldSave) {
                        viewModel.saveSession(routine)
                        onSessionComplete()
                    }
                }
            }
        },
        onRestTimeComplete = {
            viewModel.playRestTimerTone()
        }
    )
}

@Composable
@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
fun ExerciseScreen(
    modifier: Modifier = Modifier,
    routine: Routine,
    selectedExercise: RoutineExercise?,
    sessionProgress: ExerciseScreenViewModel.SessionProgress,
    exerciseProgressMap: Map<RoutineExercise, ExerciseScreenViewModel.ExerciseProgress>,
    onExerciseSelected: (RoutineExercise) -> Unit = {},
    onSessionComplete: () -> Unit = {},
    onOneRepMaxChanged: (RoutineExercise, Float?) -> Unit = { _, _ -> },
    onWeightChanged: (RoutineExercise, Float?) -> Unit = { _, _ -> },
    onPercentOneRepMaxChanged: (RoutineExercise, Float?) -> Unit = { _, _ -> },
    onUpdateExerciseProgress: () -> Unit = {},
    onRestTimeComplete: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onSessionComplete) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "back to routines"
                        )
                    }
                },
                title = { Text(text = routine.name) }
            )
        },
        content = {
            ExerciseModalSheet(
                sheetContent = {
                    selectedExercise?.let { selectedExercise ->
                        SheetContent(
                            routine = routine,
                            routineExercise = selectedExercise,
                            onOneRepMaxChanged = { oneRepMax -> onOneRepMaxChanged(selectedExercise, oneRepMax) },
                            onWeightChanged = { weight -> onWeightChanged(selectedExercise, weight) },
                            onPercentOneRepMaxChanged = { percentOneRepMax -> onPercentOneRepMaxChanged(selectedExercise, percentOneRepMax) },
                            onDoneClick = { hide() }
                        )
                    }
                },
                content = {
                    ExerciseList(
                        routineExercises = routine.exercises,
                        exerciseProgress = exerciseProgressMap,
                        currentExercise = (sessionProgress as? ExerciseScreenViewModel.SessionProgress.InProgress)?.currentExercise,
                        onExerciseClick = { routineExercise ->
                            onExerciseSelected(routineExercise)
                            show()
                        },
                        onDoneClick = {
                            onUpdateExerciseProgress()
                        },
                        onRestTimeComplete = {
                            onUpdateExerciseProgress()
                            onRestTimeComplete()
                        }
                    )
                    SessionProgressFloatingActionButton(sessionProgress) {
                        onUpdateExerciseProgress()
                    }
                }
            )
        }
    )
}

@Preview
@Composable
private fun ExerciseScreenPreview(
    @PreviewParameter(ExerciseScreenPreviewProvider::class) preview: Colors
) {
    MaterialTheme(colors = preview) {
        ExerciseScreen(
            routine = DummyAppRepository.routines.first(),
            selectedExercise = null,
            sessionProgress = ExerciseScreenViewModel.SessionProgress.InProgress(Date(), DummyAppRepository.routineExercises.first()),
            exerciseProgressMap = mapOf()
        )
    }
}
