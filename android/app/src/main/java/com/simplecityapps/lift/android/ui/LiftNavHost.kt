package com.simplecityapps.lift.android.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.simplecityapps.lift.android.ui.auth.AuthDestination
import com.simplecityapps.lift.android.ui.auth.AuthScreen
import com.simplecityapps.lift.android.ui.home.HomeScreen
import com.simplecityapps.lift.android.ui.navigation.HomeDestination
import com.simplecityapps.lift.android.ui.navigation.RoutineListDestination
import com.simplecityapps.lift.android.ui.navigation.SessionsListDestination
import com.simplecityapps.lift.android.ui.routines.RoutineListScreen
import com.simplecityapps.lift.android.ui.runtracker.MapsDestination
import com.simplecityapps.lift.android.ui.runtracker.RunTrackerScreen
import com.simplecityapps.lift.android.ui.sessions.SessionListScreen
import com.simplecityapps.lift.android.ui.sessions.detail.SessionDetailScreen
import com.simplecityapps.lift.android.ui.settings.SettingsDestination
import com.simplecityapps.lift.android.ui.settings.SettingsScreen

@Composable
fun LiftNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(MapsDestination.route) {
            RunTrackerScreen()
        }
        composable(RoutineListDestination.route) {
            RoutineListScreen(
                onNavigateToDetailScreen = { routine ->
                    navController.navigate("routines/${routine.id}")
                }
            )
        }
        composable(SessionsListDestination.route) {
            SessionListScreen()
        }
        composable(HomeDestination.route) {
            HomeScreen(
                onNavigateToSignIn = {
                    navController.navigate(AuthDestination.route)
                }
            )
        }
        composable(AuthDestination.route) {
            AuthScreen(
                onNavigateToHome = {
                    navController.navigate(HomeDestination.route)
                }
            )
        }
        composable(SettingsDestination.route) {
            SettingsScreen(
                onNavigateHome = {
                    navController.popBackStack(
                        route = HomeDestination.route,
                        inclusive = false
                    )
                }
            )
        }
        composable(
            route = "routines/{routineId}",
            arguments = listOf(
                navArgument("routineId") {
                    type = NavType.StringType
                }
            )
        ) {
            SessionDetailScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}