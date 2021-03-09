package com.acompany.weightr.features.exercises.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.acompany.data.model.Exercise
import com.acompany.weightr.features.exercises.data.ExerciseListPreviewProvider

@Composable
fun LazyExerciseList(
    exercises: List<Exercise>,
    modifier: Modifier = Modifier,
    onExerciseClick: (Exercise) -> Unit = {}
) {
    Column {
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Start Session")
        }
    }
    LazyColumn(modifier = modifier) {
        items(exercises) { exercise ->
            ExerciseListItem(exercise = exercise) {
                onExerciseClick(exercise)
            }
            Divider()
        }
    }
}

@Preview
@Composable
private fun ExerciseListPreview(
    @PreviewParameter(ExerciseListPreviewProvider::class) preview: Pair<Colors, List<Exercise>>
) {
    MaterialTheme(colors = preview.first) {
        LazyExerciseList(exercises = preview.second)
    }
}