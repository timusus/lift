package com.acompany.lift.features.exercises.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.DoubleArrow
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acompany.lift.common.components.CutCornerIcon
import com.acompany.lift.common.components.Icon
import com.acompany.lift.common.effects.remainingTimeMillis
import com.acompany.lift.data.model.RoutineExercise
import com.acompany.lift.features.exercises.data.RoutineExerciseHelper.initialWeight
import com.acompany.lift.features.exercises.data.ExerciseProgress
import com.acompany.lift.features.exercises.data.RoutineExerciseListItemPreviewProvider
import com.acompany.lift.theme.MaterialColors
import com.acompany.lift.theme.MaterialTypography

@Composable
fun ExerciseListItem(
    routineExercise: RoutineExercise,
    exerciseProgress: ExerciseProgress,
    isCurrentExercise: Boolean,
    modifier: Modifier = Modifier,
    onExerciseClick: () -> Unit = {},
    onDoneClick: () -> Unit = {},
    onRestTimeComplete: () -> Unit = {},
) {
    CompositionLocalProvider(LocalContentColor provides MaterialColors.primary) {
        Card(
            shape = CutCornerShape(topStart = 8.dp, topEnd = 4.dp, bottomStart = 4.dp, bottomEnd = 8.dp),
            backgroundColor = MaterialTheme.colors.surface,
            elevation = if (isCurrentExercise) 4.dp else 1.dp
        ) {
            Column(modifier = modifier.animateContentSize()) {
                Row(
                    modifier = Modifier
                        .heightIn(min = 72.dp)
                        .clickable(onClick = onExerciseClick)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CutCornerIcon(
                        icon = if (exerciseProgress is ExerciseProgress.Complete) Icons.Rounded.Check else Icons.Rounded.FitnessCenter,
                        modifier = Modifier.padding(8.dp),
                        tint = MaterialColors.primary
                    )
                    Spacer(modifier.size(16.dp))
                    Column(modifier.weight(1f)) {
                        Text(
                            text = routineExercise.exercise.name,
                            style = MaterialTypography.body1,
                            color = MaterialColors.onBackground
                        )
                        Text(
                            text = "${routineExercise.sets} sets x ${routineExercise.reps} reps",
                            style = MaterialTypography.body2,
                            fontSize = 16.sp,
                            color = MaterialColors.onBackground.copy(alpha = 0.85f)
                        )
                    }
                    if (routineExercise.weight != null || routineExercise.initialWeight() != null) {
                        Spacer(modifier.size(16.dp))
                        Text(
                            text = routineExercise.weight?.let { weight -> "$weight kg" }
                                ?: routineExercise.initialWeight()
                                    ?.let { initialWeight -> "$initialWeight kg" }
                                ?: "Weight",
                            style = MaterialTypography.body2,
                            fontSize = 16.sp,
                            color = MaterialColors.onBackground.copy(alpha = 0.85f)
                        )
                    }
                }
                SessionStateIndicator(exerciseProgress, routineExercise, onDoneClick, onRestTimeComplete)
            }
        }
    }
}

@Composable
private fun SessionStateIndicator(
    exerciseProgress: ExerciseProgress,
    routineExercise: RoutineExercise,
    onDoneClick: () -> Unit,
    onRestTimeComplete: () -> Unit
) {
    when (exerciseProgress) {
        is ExerciseProgress.None -> {
        }
        is ExerciseProgress.Complete -> {
        }
        is ExerciseProgress.InProgress -> {
            Row(
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Set ${exerciseProgress.set + 1} of ${routineExercise.sets}",
                    color = MaterialColors.onBackground.copy(alpha = 0.85f)
                )
                IconButton(onClick = onDoneClick) {
                    Icon(
                        icon = Icons.Rounded.DoubleArrow,
                        modifier = Modifier.padding(8.dp),
                        tint = MaterialColors.secondary
                    )
                }
            }
        }
        is ExerciseProgress.Resting -> {
            Row(
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val remainingTime = remainingTimeMillis(periodMillis = routineExercise.restPeriod * 1000L) / 1000
                val minutes = remainingTime / 60
                val seconds = remainingTime % 60
                if (remainingTime == 0L) {
                    onRestTimeComplete()
                }
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Rest for ${String.format("%02d:%02d", minutes, seconds)}",
                    color = MaterialColors.onBackground.copy(alpha = 0.85f)
                )
                IconButton(onClick = onDoneClick) {
                    Icon(
                        icon = Icons.Rounded.DoubleArrow,
                        modifier = Modifier.padding(8.dp),
                        tint = MaterialColors.secondary
                    )
                }
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
        Column(
            Modifier
                .background(preview.first.background)
                .padding(16.dp)
        ) {
            ExerciseListItem(
                routineExercise = preview.second,
                exerciseProgress = ExerciseProgress.InProgress(1),
                isCurrentExercise = true
            )
        }
    }
}
