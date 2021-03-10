package com.acompany.weightr.features.exercises.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.acompany.weightr.features.exercises.data.RoutineExerciseListPreviewProvider
import com.acompany.data.model.RoutineExercise

@Composable
fun LazyExerciseList(
    routineExercises: List<RoutineExercise>,
    modifier: Modifier = Modifier,
    onExerciseClick: (RoutineExercise) -> Unit = {},
    onExerciseLongClick: (RoutineExercise) -> Unit = {}
) {
    Column {
        Button(modifier = modifier,
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Start Session")
        }
        Spacer(Modifier.size(8.dp))
        LazyColumn(modifier = modifier) {
            items(routineExercises) { routineExercise ->
                ExerciseListItem(
                    routineExercise = routineExercise,
                    onExerciseClick = { onExerciseClick(routineExercise) },
                    onExerciseLongClick = { onExerciseLongClick(routineExercise) }
                )
                Divider()
            }
        }
    }
}

@Preview
@Composable
private fun ExerciseListPreview(
    @PreviewParameter(RoutineExerciseListPreviewProvider::class) preview: Pair<Colors, List<RoutineExercise>>
) {
    MaterialTheme(colors = preview.first) {
        LazyExerciseList(routineExercises = preview.second)
    }
}