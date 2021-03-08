package com.acompany.weightr.features.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.acompany.data.json.Exercise

@Composable
fun LazyExerciseList(
    exercises: List<Exercise>,
    modifier: Modifier = Modifier,
    onExerciseClick: (Exercise) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        items(exercises) { exercise ->
            ExerciseListItem(exercise = exercise) {
                onExerciseClick(exercise)
            }
            Divider()
        }
    }
}