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
import com.acompany.lift.data.model.RoutineExercise
import com.acompany.lift.common.components.CircleIcon
import com.acompany.lift.common.components.Icon
import com.acompany.lift.features.exercises.data.ExerciseHelper.initialWeight
import com.acompany.lift.features.exercises.data.RoutineExerciseListItemPreviewProvider
import com.acompany.lift.theme.MaterialColors
import com.acompany.lift.theme.MaterialTypography

sealed class ExerciseSessionState {
    object None : ExerciseSessionState()
    data class InProgress(val setNumber: Int) : ExerciseSessionState()
    data class Rest(val restSeconds: Int) : ExerciseSessionState()
    object Complete : ExerciseSessionState()
}

@Composable
fun ExerciseListItem(
    routineExercise: RoutineExercise,
    exerciseSessionState: ExerciseSessionState,
    modifier: Modifier = Modifier,
    onExerciseClick: () -> Unit = {}
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
                        style = MaterialTypography.body1
                    )
                    Text(
                        text = "${routineExercise.sets} sets x ${routineExercise.reps} reps",
                        style = MaterialTypography.body2,
                        fontSize = 16.sp,
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
                    )
                }
            }
            when (exerciseSessionState) {
                is ExerciseSessionState.None, ExerciseSessionState.Complete -> {

                }
                is ExerciseSessionState.InProgress -> {
                    Row(
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Set ${exerciseSessionState.setNumber} of ${routineExercise.sets}"
                        )
                        Icon(
                            icon = Icons.Rounded.CheckCircleOutline,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
                is ExerciseSessionState.Rest -> {
                    Row(
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Rest for ${exerciseSessionState.restSeconds} seconds")
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
        ExerciseListItem(routineExercise = preview.second, ExerciseSessionState.InProgress(2))
    }
}