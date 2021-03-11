package com.acompany.weightr.features.routines.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.acompany.data.AppRepository

@Composable
fun RoutineScreen(paddingValues: PaddingValues, navController: NavController, repository: AppRepository) {
    val routines by repository.getAllRoutines().collectAsState(emptyList())
    RoutineList(
        routines = routines,
        modifier = Modifier
            .padding(paddingValues)
            .padding(8.dp),
        onRoutineClick = { routine ->
            navController.navigate("routines/${routine.id}/exercises")
        }
    )
}