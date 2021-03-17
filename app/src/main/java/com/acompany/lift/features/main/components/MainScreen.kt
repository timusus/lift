package com.acompany.lift.features.main.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.StackedLineChart
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.acompany.lift.features.exercises.components.ExerciseScreen
import com.acompany.lift.features.navigation.NavDestination
import com.acompany.lift.features.routines.components.RoutineScreen
import com.acompany.lift.features.sessions.components.SessionScreen
import com.acompany.lift.theme.AppTheme

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val destinations = listOf(
        NavDestination.RoutineNavDestination,
        NavDestination.ExerciseNavDestination(),
        NavDestination.SessionNavDestination
    )

    AppTheme {
        Scaffold(bottomBar = {
            BottomAppBar {
                BottomNavigation {
                    val currentRoute = currentRoute(navController)
                    BottomNavigationItem(
                        selected = currentRoute == NavDestination.RoutineNavDestination.route,
                        onClick = { navController.navigate(NavDestination.RoutineNavDestination.route) },
                        icon = { Icon(imageVector = Icons.Rounded.Timer, contentDescription = "Routines") })
                    BottomNavigationItem(
                        selected = currentRoute == NavDestination.SessionNavDestination.route,
                        onClick = { navController.navigate(NavDestination.SessionNavDestination.route) },
                        icon = { Icon(imageVector = Icons.Rounded.StackedLineChart, contentDescription = "Progress") }
                    )
                }
            }
        }) { paddingValues ->
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
                                    modifier = Modifier.padding(paddingValues),
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
                                    modifier = Modifier.padding(paddingValues),
                                    routine = navController.previousBackStackEntry!!.arguments!!.getParcelable(
                                        NavDestination.ExerciseNavDestination.ARG_ROUTINE
                                    )!!,
                                    onSessionComplete = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                            is NavDestination.SessionNavDestination -> {
                                SessionScreen(
                                    viewModel = hiltNavGraphViewModel()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString(KEY_ROUTE)
}