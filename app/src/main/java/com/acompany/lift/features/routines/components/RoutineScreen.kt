package com.acompany.lift.features.routines.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.acompany.lift.data.model.Routine
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.features.routines.data.RoutineScreenPreviewProvider
import com.acompany.lift.features.routines.data.RoutineScreenViewModel

@Composable
fun RoutineScreen(
    viewModel: RoutineScreenViewModel,
    onRoutineSelected: (Routine) -> Unit = {}
) {
    val routines = viewModel.allRoutines.collectAsState()

    RoutineScreen(
        viewModel = viewModel,
        routines = routines.value,
        onRoutineSelected = onRoutineSelected
    )
}

@Composable
private fun RoutineScreen(
    viewModel: RoutineScreenViewModel,
    routines: List<Routine>,
    onRoutineSelected: (Routine) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Routines") },
            )
        },
        content = {
            RoutineList(
                routines = routines,
                onRoutineClick = { routine ->
                    onRoutineSelected(routine)
                }
            )
        }
    )
}

@Preview
@Composable
private fun RoutineScreenPreview(
    @PreviewParameter(RoutineScreenPreviewProvider::class) preview: Pair<Colors, RoutineScreenViewModel>
) {
    MaterialTheme(colors = preview.first) {
        RoutineScreen(preview.second, DummyAppRepository.routines)
    }
}