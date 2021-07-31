package com.acompany.lift.features.main.components

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.acompany.lift.features.home.components.Home
import com.acompany.lift.features.main.data.Screen
import com.acompany.lift.features.routines.components.RoutineList
import com.acompany.lift.features.routines.detail.components.ExerciseScreen
import com.acompany.lift.features.sessions.components.SessionList
import com.acompany.lift.features.sessions.detail.components.SessionDetailScreen

@Composable
fun LiftNavHost(
    navController: NavHostController,
    startDestination: Screen = Screen.HomeScreen
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ) {
        composable(
            route = Screen.HomeScreen.route,
            arguments = Screen.HomeScreen.arguments
        ) {
            Home(
                viewModel = hiltViewModel()
            )
        }
        composable(
            route = Screen.RoutineListScreen.route,
            arguments = Screen.RoutineListScreen.arguments
        ) {
            RoutineList(
                viewModel = hiltViewModel(),
                onRoutineSelected = { routine ->
                    navController.navigate(route = "routines/routine/${routine.id}")
                }
            )
        }
        composable(
            route = Screen.SessionListScreen.route,
            arguments = Screen.SessionListScreen.arguments
        ) {
            SessionList(
                viewModel = hiltViewModel(),
                onSessionClick = { session ->
                    navController.navigate(route = "sessions/session/${session.id}")
                })
        }
        composable(
            route = Screen.ExerciseDetailScreen().route,
            arguments = Screen.ExerciseDetailScreen().arguments
        ) {
            ExerciseScreen(
                viewModel = hiltViewModel(),
                onDismiss = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Screen.SessionDetailScreen().route,
            arguments = Screen.SessionDetailScreen().arguments
        ) {
            SessionDetailScreen(
                viewModel = hiltViewModel()
            ) {
                navController.popBackStack()
            }
        }
    }
}