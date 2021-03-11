package com.acompany.lift.features.exercises.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Colors
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acompany.lift.common.components.CircleIcon
import com.acompany.lift.data.model.RoutineExercise
import com.acompany.lift.features.exercises.data.ExerciseHelper.initialWeight
import com.acompany.lift.features.exercises.data.RoutineExerciseListItemPreviewProvider
import com.acompany.lift.theme.MaterialColors
import com.acompany.lift.theme.MaterialTypography

@Composable
fun ExerciseListItem(
    routineExercise: RoutineExercise,
    modifier: Modifier = Modifier,
    onExerciseClick: () -> Unit = {}
) {
    CompositionLocalProvider(LocalContentColor provides MaterialColors.primary) {
        Row(
            modifier = modifier
                .background(color = MaterialColors.surface)
                .heightIn(min = 72.dp)
                .clickable(onClick = onExerciseClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleIcon(
                icon = Icons.Rounded.FitnessCenter,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier.size(16.dp))
            Column(modifier.weight(1f)) {
                Text(
                    text = routineExercise.exercise.name,
                    style = MaterialTypography.body1
                )
                Text(
                    text = "${routineExercise.sets} sets x ${routineExercise.reps} reps",
                    style = MaterialTypography.body2,
                    fontSize = 16.sp,
                )
            }
            routineExercise.initialWeight()?.let { initialWeight ->
                Spacer(modifier.size(16.dp))
                Text(
                    text = routineExercise.initialWeight()?.let { "$initialWeight kg" } ?: "Weight",
                    style = MaterialTypography.body2,
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ExerciseListItemPreview(
    @PreviewParameter(RoutineExerciseListItemPreviewProvider::class) preview: Pair<Colors, RoutineExercise>
) {
    MaterialTheme(colors = preview.first) {
        ExerciseListItem(routineExercise = preview.second)
    }
}