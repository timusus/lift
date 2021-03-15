package com.acompany.lift.features.routines.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.acompany.lift.data.model.Routine

@Composable
fun RoutineScreen(
    viewModel: RoutineScreenViewModel,
    onRoutineSelected: (Routine) -> Unit
) {

    val routines = viewModel.allRoutines.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Routines") },
            )
        },
        content = {
            RoutineList(
                routines = routines.value,
                modifier = Modifier.padding(8.dp),
                onRoutineClick = { routine ->
                    onRoutineSelected(routine)
                }
            )
        }
    )
}