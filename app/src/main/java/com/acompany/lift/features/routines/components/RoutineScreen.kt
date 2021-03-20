package com.acompany.lift.features.routines.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.acompany.lift.data.model.Routine
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.features.routines.data.RoutineScreenPreviewProvider
import com.acompany.lift.features.routines.data.RoutineScreenViewModel

@Composable
fun RoutineScreen(
    viewModel: RoutineScreenViewModel,
    modifier: Modifier = Modifier,
    onRoutineSelected: (Routine) -> Unit = {}
) {
    val routines by viewModel.allRoutines.collectAsState()

    RoutineScreen(
        routines = routines,
        modifier = modifier,
        onRoutineSelected = onRoutineSelected
    )
}

@Composable
private fun RoutineScreen(
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
        RoutineScreen(DummyAppRepository.routines)
    }
}