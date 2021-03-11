package com.acompany.weightr.features.exercises.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.acompany.weightr.features.exercises.data.RoutineExerciseListPreviewProvider
import com.acompany.data.model.RoutineExercise

@Composable
fun ExerciseList(
    routineExercises: List<RoutineExercise>,
    modifier: Modifier = Modifier,
    onExerciseClick: (RoutineExercise) -> Unit = {},
    onWeightButtonClick: (RoutineExercise) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        items(routineExercises) { routineExercise ->
            ExerciseListItem(
                routineExercise = routineExercise,
                onExerciseClick = { onExerciseClick(routineExercise) },
                onWeightButtonClick = { onWeightButtonClick(routineExercise) }
            )
            Divider()
        }
    }
}

@Preview
@Composable
private fun ExerciseListPreview(
    @PreviewParameter(RoutineExerciseListPreviewProvider::class) preview: Pair<Colors, List<RoutineExercise>>
) {
    MaterialTheme(colors = preview.first) {
        ExerciseList(routineExercises = preview.second)
    }
}