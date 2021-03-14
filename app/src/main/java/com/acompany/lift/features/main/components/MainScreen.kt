package com.acompany.lift.features.main.components

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.compose.*
import com.acompany.lift.features.exercises.components.ExerciseScreen
import com.acompany.lift.features.navigation.NavDestination
import com.acompany.lift.features.routines.components.RoutineScreen
import com.acompany.lift.theme.AppTheme

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val destinations = listOf(
        NavDestination.RoutineNavDestination,
        NavDestination.ExerciseNavDestination()
    )

    AppTheme {
        NavHost(
            navController = navController,
            startDestination = NavDestination.RoutineNavDestination.route
        ) {
            destinations.forEach { screen ->
                composable(
                    route = screen.route,
                    arguments = screen.arguments
                ) { backStackEntry ->
                    when (screen) {
                        is NavDestination.RoutineNavDestination -> {
                            RoutineScreen(
                                viewModel = hiltNavGraphViewModel(),
                                onRoutineSelected = { routine ->
                                    navController.navigate("routines/${routine.id}/exercises")
                                }
                            )
                        }
                        is NavDestination.ExerciseNavDestination -> {
                            ExerciseScreen(
                                viewModel = hiltNavGraphViewModel(),
                                backStackEntry.arguments!!.getLong(NavDestination.ExerciseNavDestination.ARG_ROUTINE_ID)
                            )
                        }
                    }
                }
            }
        }
    }
}