package com.acompany.lift.features.exercises.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.acompany.lift.data.model.Routine
import com.acompany.lift.features.exercises.data.ExerciseScreenPreviewProvider
import com.acompany.lift.features.exercises.data.ExerciseScreenViewModel
import com.acompany.lift.features.main.data.DummyAppRepository

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
                            onOneRepMaxChanged = { oneRepMax ->
                                viewModel.updateOneRepMax(
                                    selectedExercise.id,
                                    oneRepMax
                                )
                            },
                            onWeightChanged = { weight ->
                                viewModel.updateRoutineExerciseWeight(
                                    selectedExercise.id,
                                    weight
                                )
                            },
                            onPercentOneRepMaxChanged = { percentOneRepMax ->
                                viewModel.updateRoutineExercisePercentOneRepMax(
                                    selectedExercise.id,
                                    percentOneRepMax
                                )
                            },
                            onDoneClick = { hide() }
                        )
                    }
                },
                content = {
                    ExerciseList(
                        modifier = Modifier.padding(8.dp),
                        routineExercises = routine.exercises,
                        exerciseProgress = viewModel.exerciseProgressMap,
                        onExerciseClick = { routineExercise ->
                            viewModel.setSelectedRoutineExercise(routineExercise)
                            show()
                        },
                        onDoneClick = { viewModel.moveToNext(routine) }
                    )
                    SessionProgressFloatingActionButton(viewModel.sessionProgress) {
                        viewModel.moveToNext(routine)
                    }
                }
            )
        })
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
