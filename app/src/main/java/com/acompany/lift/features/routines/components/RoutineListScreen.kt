package com.acompany.lift.features.routines.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.acompany.lift.data.model.Routine
import com.acompany.lift.features.main.components.LiftBottomNavigation
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.features.main.data.NavDestination
import com.acompany.lift.features.routines.data.RoutineScreenPreviewProvider
import com.acompany.lift.features.routines.data.RoutineScreenViewModel

@Composable
fun RoutineListScreen(
    viewModel: RoutineScreenViewModel,
    modifier: Modifier = Modifier,
    currentRoute: String?,
    onRoutineSelected: (Routine) -> Unit,
    onNavigate: (String) -> Unit
) {
    val routines by viewModel.allRoutines.collectAsState()

    RoutineListScreen(
        routines = routines,
        modifier = modifier,
        currentRoute = currentRoute,
        onRoutineSelected = onRoutineSelected,
        onNavigate = onNavigate
    )
}

@Composable
private fun RoutineListScreen(
    routines: List<Routine>,
    modifier: Modifier = Modifier,
    currentRoute: String?,
    onRoutineSelected: (Routine) -> Unit = {},
    onNavigate: (String) -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Routines") },
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                RoutineList(
                    routines = routines,
                    onRoutineClick = { routine ->
                        onRoutineSelected(routine)
                    }
                )
            }
        },
        bottomBar = {
            LiftBottomNavigation(currentRoute) { item ->
                onNavigate(item.destination.route)
            }
        }
    )
}

@Preview
@Composable
private fun RoutineScreenPreview(
    @PreviewParameter(RoutineScreenPreviewProvider::class) preview: Pair<Colors, RoutineScreenViewModel>
) {
    MaterialTheme(colors = preview.first) {
        RoutineListScreen(DummyAppRepository.routines, currentRoute = NavDestination.RoutineNavDestination.route)
    }
}