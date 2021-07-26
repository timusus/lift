package com.acompany.lift.features.main.components

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.acompany.lift.features.home.components.HomeScreen
import com.acompany.lift.features.main.data.NavDestination
import com.acompany.lift.features.routines.components.RoutineListScreen
import com.acompany.lift.features.routines.detail.components.ExerciseScreen
import com.acompany.lift.features.sessions.components.SessionListScreen
import com.acompany.lift.features.sessions.detail.components.SessionDetailScreen

@Composable
fun LiftNavHost(
    startDestination: NavDestination = NavDestination.HomeNavDestination
) {
    val navController = rememberNavController()

    val destinations = listOf(
        NavDestination.HomeNavDestination,
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
                    is NavDestination.HomeNavDestination -> {
                        HomeScreen(
                            viewModel = hiltViewModel(),
                            currentRoute = navController.currentBackStackEntry?.destination?.route,
                            onNavigate = { route ->
                                navController.navigate(route)
                            })
                    }
                    is NavDestination.RoutineNavDestination -> {
                        RoutineListScreen(
                            viewModel = hiltViewModel(),
                            currentRoute = navController.currentBackStackEntry?.destination?.route,
                            onRoutineSelected = { routine ->
                                navController.navigate(route = "routines/routine/${routine.id}")
                            },
                            onNavigate = { route ->
                                navController.navigate(route)
                            }
                        )
                    }
                    is NavDestination.ExerciseNavDestination -> {
                        ExerciseScreen(
                            viewModel = hiltViewModel(),
                            currentRoute = navController.currentBackStackEntry?.destination?.route,
                            onDismiss = {
                                navController.popBackStack()
                            },
                            onNavigate = { route ->
                                navController.navigate(route)
                            }
                        )
                    }
                    is NavDestination.SessionNavDestination -> {
                        SessionListScreen(
                            viewModel = hiltViewModel(),
                            currentRoute = navController.currentBackStackEntry?.destination?.route,
                            onSessionClick = { session ->
                                navController.navigate(route = "sessions/session/${session.id}")
                            },
                            onNavigate = { route ->
                                navController.navigate(route)
                            })
                    }
                    is NavDestination.SessionDetailNavDestination -> {
                        SessionDetailScreen(
                            viewModel = hiltViewModel(),
                            currentRoute = navController.currentBackStackEntry?.destination?.route,
                            onNavigate = { route ->
                                navController.navigate(route)
                            },
                            onDismiss = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}