package com.acompany.lift.features.routines.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Colors
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.acompany.lift.data.model.Routine
import com.acompany.lift.features.routines.data.RoutineListPreviewProvider

@Composable
fun RoutineList(
    routines: List<Routine>,
    modifier: Modifier = Modifier,
    onRoutineClick: (Routine) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        items(routines) { routine ->
            RoutineListItem(
                routine = routine,
            ) {
                onRoutineClick(routine)
            }
            Divider()
        }
    }
}

@Preview
@Composable
private fun SessionListItemPreview(
    @PreviewParameter(RoutineListPreviewProvider::class) preview: Pair<Colors, List<Routine>>
) {
    MaterialTheme(colors = preview.first) {
        RoutineList(routines = preview.second)
    }
}
