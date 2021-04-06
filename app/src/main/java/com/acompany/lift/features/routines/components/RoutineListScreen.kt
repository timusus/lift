package com.acompany.lift.features.routines.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.acompany.lift.data.model.Exercise
import com.acompany.lift.data.model.Routine
import com.acompany.lift.features.main.components.LiftBottomNavigation
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.features.main.data.NavDestination
import com.acompany.lift.features.routines.data.RoutineListScreenViewModel
import com.acompany.lift.features.routines.data.RoutineScreenPreviewProvider
import com.acompany.lift.features.routines.detail.components.bottomEndFabPlacement
import kotlinx.coroutines.launch

@Composable
fun RoutineListScreen(
    viewModel: RoutineListScreenViewModel,
    modifier: Modifier = Modifier,
    currentRoute: String?,
    onRoutineSelected: (Routine) -> Unit,
    onNavigate: (String) -> Unit
) {
    val routines by viewModel.allRoutines.collectAsState()
    val exercises by viewModel.allExercises.collectAsState()

    RoutineListScreen(
        routines = routines,
        allExercises = exercises,
        modifier = modifier,
        currentRoute = currentRoute,
        onRoutineSelected = onRoutineSelected,
        onNavigate = onNavigate,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RoutineListScreen(
    routines: List<Routine>,
    allExercises: List<Exercise>,
    modifier: Modifier = Modifier,
    currentRoute: String?,
    onRoutineSelected: (Routine) -> Unit = {},
    onNavigate: (String) -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetShape = CutCornerShape(topStart = 8.dp, topEnd = 4.dp, bottomStart = 4.dp, bottomEnd = 4.dp),
        sheetState = sheetState,
        content = {
            Scaffold(
                modifier = modifier,
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Routines") }
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
                        FloatingActionButton(
                            modifier = modifier.bottomEndFabPlacement(),
                            onClick = {
                                scope.launch {
                                    sheetState.show()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Add,
                                contentDescription = "Add Routine",
                                tint = MaterialTheme.colors.onSurface
                            )
                        }
                    }
                },
                bottomBar = {
                    LiftBottomNavigation(currentRoute) { item ->
                        onNavigate(item.route)
                    }
                }
            )
        },
        sheetContent = {
            RoutineListBottomSheetContent(
                modifier = modifier,
                allExercises = allExercises
            )
        }
    )
}

@Preview
@Composable
private fun RoutineScreenPreview(
    @PreviewParameter(RoutineScreenPreviewProvider::class) preview: Pair<Colors, RoutineListScreenViewModel>
) {
    MaterialTheme(colors = preview.first) {
        RoutineListScreen(
            routines = DummyAppRepository.routines,
            allExercises = DummyAppRepository.exercises,
            currentRoute = NavDestination.RoutineNavDestination.route
        )
    }
}