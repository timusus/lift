package com.acompany.lift.features.exercises.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.acompany.lift.data.model.RoutineExercise
import com.acompany.lift.features.exercises.data.ExerciseProgress
import com.acompany.lift.features.exercises.data.RoutineExerciseListPreviewProvider

@Composable
fun ExerciseList(
    routineExercises: List<RoutineExercise>,
    exerciseProgress: Map<Long, ExerciseProgress>,
    currentRoutineExerciseId: Long?,
    modifier: Modifier = Modifier,
    onExerciseClick: (RoutineExercise) -> Unit = {},
    onDoneClick: (RoutineExercise) -> Unit = {},
    onRestTimeComplete: (RoutineExercise) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(routineExercises) { routineExercise ->
            ExerciseListItem(
                routineExercise = routineExercise,
                exerciseProgress = exerciseProgress[routineExercise.id] ?: ExerciseProgress.None,
                isCurrentExercise = routineExercise.id == currentRoutineExerciseId,
                onExerciseClick = { onExerciseClick(routineExercise) },
                onDoneClick = { onDoneClick(routineExercise) },
                onRestTimeComplete = { onRestTimeComplete(routineExercise) }
            )
        }
    }
}

@Preview
@Composable
private fun ExerciseListPreview(
    @PreviewParameter(RoutineExerciseListPreviewProvider::class) preview: Pair<Colors, List<RoutineExercise>>
) {
    MaterialTheme(colors = preview.first) {
        ExerciseList(
            routineExercises = preview.second,
            mapOf(preview.second.first().id to ExerciseProgress.None),
            preview.second.first().id
        )
    }
}
