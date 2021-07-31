package com.acompany.lift.features.main.components

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.acompany.lift.features.main.data.Screen
import com.acompany.lift.theme.AppTheme

@Composable
fun LiftApp() {

    val navController = rememberNavController()

    AppTheme {
        val navBackStackEntry = navController.currentBackStackEntryAsState().value
        val currentDestination = navBackStackEntry?.destination
        val currentScreen = Screen.allScreens.firstOrNull { it.route == currentDestination?.route }

        Scaffold(
            bottomBar = {
                if (currentScreen is Screen.RootScreen) {
                    LiftBottomNavigation(Screen.rootScreens, currentDestination = currentDestination) { screen ->
                        navController.navigate(screen.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }
        ) {
            LiftNavHost(navController)
        }
    }
}