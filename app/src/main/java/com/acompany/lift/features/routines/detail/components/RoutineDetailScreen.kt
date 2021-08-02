package com.acompany.lift.features.routines.detail.components

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
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.features.routines.detail.data.*
import java.util.*

@Composable
@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
fun ExerciseScreen(
    modifier: Modifier = Modifier,
    viewModel: RoutineDetailViewModel,
    onDismiss: () -> Unit
) {
    val screenState: ScreenState by viewModel.screenState.collectAsState()
    var selectedExercise by rememberSaveable { mutableStateOf<RoutineExercise?>(null) }

    ExerciseScreen(
        modifier = modifier,
        screenState = screenState,
        selectedExercise = selectedExercise,
        routineProgress = viewModel.routineProgress,
        exerciseProgressMap = viewModel.exerciseProgressMap,
        onExerciseSelected = { routineExercise ->
            selectedExercise = routineExercise
        },
        onDismiss = onDismiss,
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
                is RoutineProgress.Complete -> {
                    viewModel.saveSession(routine)
                    onDismiss()
                }
            }
        }
    ) {
        viewModel.playRestTimerTone()
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
fun ExerciseScreen(
    modifier: Modifier = Modifier,
    screenState: ScreenState,
    selectedExercise: RoutineExercise?,
    routineProgress: RoutineProgress,
    exerciseProgressMap: Map<Long, ExerciseProgress>,
    onExerciseSelected: (RoutineExercise) -> Unit = {},
    onDismiss: () -> Unit = {},
    onOneRepMaxChanged: (RoutineExercise, Float?) -> Unit = { _, _ -> },
    onWeightChanged: (RoutineExercise, Float?) -> Unit = { _, _ -> },
    onPercentOneRepMaxChanged: (RoutineExercise, Float?) -> Unit = { _, _ -> },
    onUpdateExerciseProgress: (Routine) -> Unit = {},
    onRestTimeComplete: () -> Unit = {}
) {
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
            Scaffold(
                modifier = modifier,
                topBar = {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(onClick = onDismiss) {
                                Icon(
                                    imageVector = Icons.Rounded.ArrowBack,
                                    contentDescription = "back to routines"
                                )
                            }
                        },
                        title = { Text(text = (screenState as? ScreenState.Ready)?.routine?.name ?: "") }
                    )
                },
                content = { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
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
                                    currentRoutineExerciseId = (routineProgress as? RoutineProgress.InProgress)?.currentRoutineExerciseId,
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
                                SessionProgressFloatingActionButton(routineProgress) {
                                    onUpdateExerciseProgress(screenState.routine)
                                }
                            }
                        }
                    }
                }
            )
        })
}

@Preview
@Composable
private fun ExerciseScreenPreview(
    @PreviewParameter(RoutineDetailScreenPreviewProvider::class) preview: Colors
) {
    MaterialTheme(colors = preview) {
        ExerciseScreen(
            screenState = ScreenState.Ready(DummyAppRepository.routines.first()),
            selectedExercise = null,
            routineProgress = RoutineProgress.InProgress(Date(), DummyAppRepository.routineExercises.first().id),
            exerciseProgressMap = mapOf()
        )
    }
}
