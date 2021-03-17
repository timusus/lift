package com.acompany.lift.features.exercises.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.acompany.lift.data.model.Routine
import com.acompany.lift.features.exercises.data.ExerciseScreenPreviewProvider
import com.acompany.lift.features.exercises.data.ExerciseScreenViewModel
import com.acompany.lift.features.main.data.DummyAppRepository

@Composable
@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
fun ExerciseScreen(
    viewModel: ExerciseScreenViewModel,
    routine: Routine,
    modifier: Modifier = Modifier,
    onSessionComplete: () -> Unit
) {
    val routine: Routine by viewModel.getRoutine(routine.id).collectAsState(initial = routine)
    val selectedExercise by viewModel.selectedRoutineExercise.collectAsState()

    fun updateProgress(routine: Routine) {
        viewModel.updateProgress(routine)
        when (val sessionProgress = viewModel.sessionProgress) {
            is ExerciseScreenViewModel.SessionProgress.Complete -> {
                if (sessionProgress.shouldSave) {
                    viewModel.saveSession(routine)
                    onSessionComplete()
                }
            }
        }
    }

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
                            onOneRepMaxChanged = { oneRepMax ->
                                viewModel.updateOneRepMax(
                                    exerciseId = selectedExercise.id,
                                    value = oneRepMax
                                )
                            },
                            onWeightChanged = { weight ->
                                viewModel.updateRoutineExerciseWeight(
                                    exerciseId = selectedExercise.id,
                                    value = weight
                                )
                            },
                            onPercentOneRepMaxChanged = { percentOneRepMax ->
                                viewModel.updateRoutineExercisePercentOneRepMax(
                                    exerciseId = selectedExercise.id,
                                    value = percentOneRepMax
                                )
                            },
                            onDoneClick = { hide() }
                        )
                    }
                },
                content = {
                    ExerciseList(
                        routineExercises = routine.exercises,
                        exerciseProgress = viewModel.exerciseProgressMap,
                        currentExercise = (viewModel.sessionProgress as? ExerciseScreenViewModel.SessionProgress.InProgress)?.currentExercise,
                        onExerciseClick = { routineExercise ->
                            viewModel.setSelectedRoutineExercise(routineExercise)
                            show()
                        },
                        onDoneClick = {
                            updateProgress(routine)
                        }
                    )
                    SessionProgressFloatingActionButton(viewModel.sessionProgress) {
                        updateProgress(routine)
                    }
                }
            )
        }
    )
}

@Preview
@Composable
private fun ExerciseScreenPreview(
    @PreviewParameter(ExerciseScreenPreviewProvider::class) preview: Pair<Colors, ExerciseScreenViewModel>
) {
    MaterialTheme(colors = preview.first) {
        ExerciseScreen(preview.second, DummyAppRepository.routines.first()) {}
    }
}
