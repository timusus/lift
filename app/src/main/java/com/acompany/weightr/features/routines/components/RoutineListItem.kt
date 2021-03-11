package com.acompany.weightr.features.routines.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.acompany.data.model.Routine
import com.acompany.data.model.RoutineExercise
import com.acompany.weightr.common.components.CircleIcon
import com.acompany.weightr.features.routines.data.RoutineListItemPreviewProvider
import com.acompany.weightr.theme.MaterialColors
import com.acompany.weightr.theme.MaterialTypography

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun RoutineListItem(
    routine: Routine,
    modifier: Modifier = Modifier,
    onSessionClick: () -> Unit = {}
) {
    CompositionLocalProvider(LocalContentColor provides MaterialColors.primary) {
        Row(
            modifier
                .background(color = MaterialColors.surface)
                .heightIn(min = 72.dp)
                .clickable(onClick = onSessionClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleIcon(
                icon = Icons.Rounded.FitnessCenter,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.size(16.dp))
            Column(modifier.weight(1f)) {
                Text(
                    text = routine.name,
                    style = MaterialTypography.body1
                )
                Text(
                    text = routine.exercises.joinToString(", ") { it.exercise.name },
                    style = MaterialTypography.body2
                )
            }
        }
    }
}

@Preview
@Composable
private fun SessionListItemPreview(
    @PreviewParameter(RoutineListItemPreviewProvider::class) preview: Pair<Colors, Pair<Routine, List<RoutineExercise>>>
) {
    MaterialTheme(colors = preview.first) {
        RoutineListItem(routine = preview.second.first)
    }
}