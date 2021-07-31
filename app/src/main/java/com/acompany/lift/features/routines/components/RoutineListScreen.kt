package com.acompany.lift.features.routines.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.acompany.lift.data.model.Routine
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.features.routines.data.RoutineListScreenViewModel
import com.acompany.lift.features.routines.data.RoutineScreenPreviewProvider

@Composable
fun RoutineList(
    viewModel: RoutineListScreenViewModel,
    modifier: Modifier = Modifier,
    onRoutineSelected: (Routine) -> Unit = {}
) {
    val routines by viewModel.allRoutines.collectAsState()

    RoutineList(
        routines = routines,
        modifier = modifier,
        onRoutineSelected = onRoutineSelected
    )
}

@Composable
private fun RoutineList(
    routines: List<Routine>,
    modifier: Modifier = Modifier,
    onRoutineSelected: (Routine) -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Routines") },
            )
        }) {
        Box(modifier = modifier.semantics { contentDescription = "Session List Screen" }) {
            LazyRoutineList(
                routines = routines,
                onRoutineClick = { routine ->
                    onRoutineSelected(routine)
                }
            )
        }
    }
}

@Preview
@Composable
private fun RoutineScreenPreview(
    @PreviewParameter(RoutineScreenPreviewProvider::class) preview: Pair<Colors, RoutineListScreenViewModel>
) {
    MaterialTheme(colors = preview.first) {
        RoutineList(DummyAppRepository.routines)
    }
}