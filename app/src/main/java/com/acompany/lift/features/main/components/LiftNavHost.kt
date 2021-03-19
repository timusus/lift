package com.acompany.lift.features.main.components

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import com.acompany.lift.features.exercises.components.ExerciseScreen
import com.acompany.lift.features.main.data.NavDestination
import com.acompany.lift.features.main.data.NavDestination.SessionDetailNavDestination.Companion.ARG_SESSION
import com.acompany.lift.features.routines.components.RoutineScreen
import com.acompany.lift.features.sessions.components.SessionDetailScreen
import com.acompany.lift.features.sessions.components.SessionScreen

@Composable
fun ListNavHost(
    navController: NavHostController,
    startDestination: NavDestination = NavDestination.RoutineNavDestination
) {
    val destinations = listOf(
        NavDestination.RoutineNavDestination,
        NavDestination.ExerciseNavDestination(),
        NavDestination.SessionNavDestination,
        NavDestination.SessionDetailNavDestination()
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
                    is NavDestination.RoutineNavDestination -> {
                        RoutineScreen(
                            viewModel = hiltNavGraphViewModel(),
                            onRoutineSelected = { routine ->
                                navController.navigate(route = "routines/routine/${routine.id}")
                            }
                        )
                    }
                    is NavDestination.ExerciseNavDestination -> {
                        ExerciseScreen(
                            viewModel = hiltNavGraphViewModel(),
                            onSessionComplete = {
                                navController.popBackStack()
                            }
                        )
                    }
                    is NavDestination.SessionNavDestination -> {
                        SessionScreen(viewModel = hiltNavGraphViewModel(),
                            onSessionClick = { session ->
                                navController.currentBackStackEntry?.arguments!!.putParcelable(ARG_SESSION, session)
                                navController.navigate(route = "sessions/session")
                            })
                    }
                    is NavDestination.SessionDetailNavDestination -> {
                        val previousArguments = navController.previousBackStackEntry!!.arguments!!
                        SessionDetailScreen(
                            viewModel = hiltNavGraphViewModel(),
                            session = previousArguments.getParcelable(ARG_SESSION)!!, onSessionDeleted = {
                                navController.popBackStack()
                            })
                    }
                }
            }
        }
    }
}