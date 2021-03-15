package com.acompany.lift.features.exercises.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircleOutline
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
import com.acompany.lift.common.components.Icon
import com.acompany.lift.common.components.remainingTimeMillis
import com.acompany.lift.data.model.RoutineExercise
import com.acompany.lift.features.exercises.data.ExerciseHelper.initialWeight
import com.acompany.lift.features.exercises.data.RoutineExerciseListItemPreviewProvider
import com.acompany.lift.theme.MaterialColors
import com.acompany.lift.theme.MaterialTypography

@Composable
fun ExerciseListItem(
    routineExercise: RoutineExercise,
    sessionState: ExerciseScreenViewModel.SessionState,
    modifier: Modifier = Modifier,
    onExerciseClick: () -> Unit = {},
    onActionClick: () -> Unit = {}
) {
    CompositionLocalProvider(LocalContentColor provides MaterialColors.primary) {
        Column {
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
                            ?: routineExercise.initialWeight()?.let { initialWeight -> "$initialWeight kg" }
                            ?: "Weight",
                        style = MaterialTypography.body2,
                        fontSize = 16.sp,
                        color = MaterialColors.onBackground.copy(alpha = 0.85f)
                    )
                }
            }
            SessionStateIndicator(sessionState, routineExercise, onActionClick)
        }
    }
}

@Composable
private fun SessionStateIndicator(
    sessionState: ExerciseScreenViewModel.SessionState,
    routineExercise: RoutineExercise,
    onActionClick: () -> Unit
) {
    when (sessionState) {
        is ExerciseScreenViewModel.SessionState.Complete, ExerciseScreenViewModel.SessionState.None -> {

        }
        is ExerciseScreenViewModel.SessionState.InProgress -> {
            if (sessionState.exercise == routineExercise) {
                Row(
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Set ${sessionState.set + 1} of ${routineExercise.sets}",
                        color = MaterialColors.onBackground.copy(alpha = 0.85f)
                    )
                    IconButton(onClick = onActionClick) {
                        Icon(
                            icon = Icons.Rounded.CheckCircleOutline,
                            modifier = Modifier.padding(8.dp),
                            tint = MaterialColors.secondary
                        )
                    }
                }
            }
        }
        is ExerciseScreenViewModel.SessionState.Resting -> {
            if (sessionState.exercise == routineExercise) {
                Row(
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val remainingTime = remainingTimeMillis(periodMillis = sessionState.exercise.restPeriod * 1000L) / 1000
                    val minutes = remainingTime / 60
                    val seconds = remainingTime % 60
                    if (remainingTime == 0L) {
                        onActionClick()
                    }
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Rest for ${String.format("%02d:%02d", minutes, seconds)}",
                        color = MaterialColors.onBackground.copy(alpha = 0.85f)
                    )
                    IconButton(onClick = onActionClick) {
                        Icon(
                            icon = Icons.Rounded.CheckCircleOutline,
                            modifier = Modifier.padding(8.dp),
                            tint = MaterialColors.secondary
                        )
                    }
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
        ExerciseListItem(routineExercise = preview.second, ExerciseScreenViewModel.SessionState.None)
    }
}