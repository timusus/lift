package com.acompany.lift.features.main.components

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import com.acompany.lift.features.exercises.components.ExerciseScreen
import com.acompany.lift.features.main.data.NavDestination
import com.acompany.lift.features.main.data.NavDestination.ExerciseNavDestination.Companion.ARG_ROUTINE
import com.acompany.lift.features.routines.components.RoutineScreen
import com.acompany.lift.features.sessions.components.SessionScreen

@Composable
fun ListNavHost(
    navController: NavHostController,
    startDestination: NavDestination = NavDestination.RoutineNavDestination
) {
    val destinations = listOf(
        NavDestination.RoutineNavDestination,
        NavDestination.ExerciseNavDestination(),
        NavDestination.SessionNavDestination
    )
    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ) {
        destinations.forEach { destination ->
            composable(
                route = destination.route,
                arguments = destination.arguments
            ) {
                when (destination) {
                    NavDestination.RoutineNavDestination -> {
                        val currentArguments = navController.currentBackStackEntry!!.arguments!!
                        RoutineScreen(
                            viewModel = hiltNavGraphViewModel(),
                            onRoutineSelected = { routine ->
                                currentArguments.putParcelable(ARG_ROUTINE, routine)
                                navController.navigate(route = "routines/routine/exercises")
                            }
                        )
                    }
                    is NavDestination.ExerciseNavDestination -> {
                        val previousArguments = navController.previousBackStackEntry!!.arguments!!
                        ExerciseScreen(
                            viewModel = hiltNavGraphViewModel(),
                            routine = previousArguments.getParcelable(ARG_ROUTINE)!!,
                            onSessionComplete = {
                                navController.popBackStack()
                            }
                        )
                    }
                    NavDestination.SessionNavDestination -> {
                        SessionScreen(viewModel = hiltNavGraphViewModel())
                    }
                }
            }
        }
    }
}