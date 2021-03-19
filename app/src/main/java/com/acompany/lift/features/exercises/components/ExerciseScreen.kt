package com.acompany.lift.features.exercises.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.acompany.lift.data.model.Routine
import com.acompany.lift.data.model.RoutineExercise
import com.acompany.lift.features.exercises.data.*
import com.acompany.lift.features.main.data.DummyAppRepository
import java.util.*

@Composable
@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
fun ExerciseScreen(
    modifier: Modifier = Modifier,
    viewModel: ExerciseScreenViewModel,
    onSessionComplete: () -> Unit,
) {

    val screenState: ScreenState by viewModel.screenState.collectAsState()
    var selectedExercise by rememberSaveable { mutableStateOf<RoutineExercise?>(null) }

    ExerciseScreen(
        modifier = modifier,
        screenState = screenState,
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
        onUpdateExerciseProgress = { routine ->
            viewModel.updateProgress(routine)
            when (val sessionProgress = viewModel.sessionProgress) {
                is SessionProgress.Complete -> {
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
    screenState: ScreenState,
    selectedExercise: RoutineExercise?,
    sessionProgress: SessionProgress,
    exerciseProgressMap: Map<RoutineExercise, ExerciseProgress>,
    onExerciseSelected: (RoutineExercise) -> Unit = {},
    onSessionComplete: () -> Unit = {},
    onOneRepMaxChanged: (RoutineExercise, Float?) -> Unit = { _, _ -> },
    onWeightChanged: (RoutineExercise, Float?) -> Unit = { _, _ -> },
    onPercentOneRepMaxChanged: (RoutineExercise, Float?) -> Unit = { _, _ -> },
    onUpdateExerciseProgress: (Routine) -> Unit = {},
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
                title = { Text(text = (screenState as? ScreenState.Ready)?.routine?.name ?: "Lift") }
            )
        },
        content = {
            ExerciseModalSheet(
                sheetContent = {
                    selectedExercise?.let { selectedExercise ->
                        when (screenState) {
                            is ScreenState.Loading -> {
                                Box(
                                    modifier = modifier.fillMaxSize(),
                                    contentAlignment = Alignment.TopCenter
                                ) {
                                    CircularProgressIndicator(modifier.padding(top = 16.dp))
                                }
                            }
                            is ScreenState.Ready -> {
                                SheetContent(
                                    routine = screenState.routine,
                                    routineExercise = selectedExercise,
                                    onOneRepMaxChanged = { oneRepMax -> onOneRepMaxChanged(selectedExercise, oneRepMax) },
                                    onWeightChanged = { weight -> onWeightChanged(selectedExercise, weight) },
                                    onPercentOneRepMaxChanged = { percentOneRepMax -> onPercentOneRepMaxChanged(selectedExercise, percentOneRepMax) },
                                    onDoneClick = { hide() }
                                )
                            }
                        }
                    }
                },
                content = {
                    when (screenState) {
                        is ScreenState.Loading -> {
                            Box(
                                modifier = modifier.fillMaxSize(),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                CircularProgressIndicator(modifier.padding(top = 16.dp))
                            }
                        }
                        is ScreenState.Ready -> {
                            ExerciseList(
                                routineExercises = screenState.routine.exercises,
                                exerciseProgress = exerciseProgressMap,
                                currentExercise = (sessionProgress as? SessionProgress.InProgress)?.currentExercise,
                                onExerciseClick = { routineExercise ->
                                    onExerciseSelected(routineExercise)
                                    show()
                                },
                                onDoneClick = {
                                    onUpdateExerciseProgress(screenState.routine)
                                },
                                onRestTimeComplete = {
                                    onUpdateExerciseProgress(screenState.routine)
                                    onRestTimeComplete()
                                }
                            )
                            SessionProgressFloatingActionButton(sessionProgress) {
                                onUpdateExerciseProgress(screenState.routine)
                            }
                        }
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
            screenState = ScreenState.Ready(DummyAppRepository.routines.first()),
            selectedExercise = null,
            sessionProgress = SessionProgress.InProgress(Date(), DummyAppRepository.routineExercises.first()),
            exerciseProgressMap = mapOf()
        )
    }
}
