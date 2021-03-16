package com.acompany.lift.features.main.components

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
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
                ) {
                    when (screen) {
                        is NavDestination.RoutineNavDestination -> {
                            RoutineScreen(
                                viewModel = hiltNavGraphViewModel(),
                                onRoutineSelected = { routine ->
                                    navController.currentBackStackEntry!!.arguments!!.putParcelable(
                                        NavDestination.ExerciseNavDestination.ARG_ROUTINE, routine
                                    )
                                    navController.navigate("routines/routine/exercises")
                                }
                            )
                        }
                        is NavDestination.ExerciseNavDestination -> {
                            ExerciseScreen(
                                viewModel = hiltNavGraphViewModel(),
                                routine = navController.previousBackStackEntry!!.arguments!!.getParcelable(
                                    NavDestination.ExerciseNavDestination.ARG_ROUTINE
                                )!!
                            )
                        }
                    }
                }
            }
        }
    }
}