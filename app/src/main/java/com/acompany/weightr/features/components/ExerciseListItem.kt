package com.acompany.weightr.features.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.acompany.data.json.Exercise
import com.acompany.weightr.common.components.CircleIcon
import com.acompany.weightr.common.components.OverflowMenuButton
import com.acompany.weightr.features.data.ExercisePreviewProvider
import com.acompany.weightr.theme.MaterialColors
import com.acompany.weightr.theme.MaterialTypography

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun ExerciseListItem(
    exercise: Exercise,
    modifier: Modifier = Modifier,
    onExerciseClick: () -> Unit = {}
) {
    CompositionLocalProvider(LocalContentColor provides MaterialColors.primary) {
        ListItem(
            icon = {
                CircleIcon(
                    icon = Icons.Rounded.FitnessCenter,
                    modifier = Modifier.padding(8.dp)
                )
            },
            text = {
                Text(
                    text = exercise.name,
                    style = MaterialTypography.body1
                )
            },
            secondaryText = {
                Text(
                    text = "${exercise.reps} - ${exercise.sets}",
                    style = MaterialTypography.body2
                )
            },
            trailing = {
                OverflowMenuButton(menuItems = emptyList())
            },
            modifier = modifier
                .background(color = MaterialColors.surface)
                .clickable(onClick = onExerciseClick)
        )
    }
}

@Preview
@Composable
private fun ExerciseListItemPreview(
    @PreviewParameter(ExercisePreviewProvider::class) preview: Pair<Colors, Exercise>
) {
    MaterialTheme(colors = preview.first) {
        ExerciseListItem(exercise = preview.second)
    }
}