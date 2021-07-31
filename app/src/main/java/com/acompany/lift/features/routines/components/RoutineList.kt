package com.acompany.lift.features.routines.components

import androidx.compose.foundation.background
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
import com.acompany.lift.data.model.Routine
import com.acompany.lift.features.routines.data.RoutineListPreviewProvider

@Composable
fun LazyRoutineList(
    routines: List<Routine>,
    modifier: Modifier = Modifier,
    onRoutineClick: (Routine) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(routines) { routine ->
            RoutineListItem(
                routine = routine,
            ) {
                onRoutineClick(routine)
            }
        }
    }
}

@Preview
@Composable
private fun SessionListItemPreview(
    @PreviewParameter(RoutineListPreviewProvider::class) preview: Pair<Colors, List<Routine>>
) {
    MaterialTheme(colors = preview.first) {
        LazyRoutineList(routines = preview.second, modifier = Modifier.background(preview.first.background))
    }
}
