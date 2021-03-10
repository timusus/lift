package com.acompany.weightr.features.routines.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.navigate
import com.acompany.data.AppRepository
import com.acompany.weightr.features.Screen
import kotlinx.coroutines.flow.combine

class RoutineScreen(repository: AppRepository) : Screen(
    route = "routines",
    name = "Routines",
    content = { paddingValues, navController, navBackStackEntry ->
        val routines by repository.getRoutines().collectAsState(emptyList())
        LazyRoutineList(
            routines = routines,
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            onRoutineClick = { routine ->
                navController.navigate("routines/${routine.id}/exercises")
            }
        )
    }
)