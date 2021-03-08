package com.acompany.weightr.features.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.acompany.data.json.Exercise
import com.acompany.weightr.common.components.OverflowMenuButton
import com.acompany.weightr.features.data.ExercisePreviewProvider
import com.acompany.weightr.theme.MaterialColors

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun ExerciseListItem(
    exercise: Exercise,
    modifier: Modifier = Modifier,
    onExerciseClick: () -> Unit = {}
) {
    CompositionLocalProvider(LocalContentColor provides MaterialColors.onBackground) {
        ListItem(
            text = {
                Text(text = exercise.name)
            },
            secondaryText = {
                Text(text = "${exercise.reps} - ${exercise.sets}")
            },
            trailing = {
                OverflowMenuButton(menuItems = emptyList())
            },
            modifier = modifier
                .background(color = MaterialColors.background)
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