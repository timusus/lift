package com.simplecityapps.lift.android.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.simplecityapps.lift.android.ui.navigation.TOP_LEVEL_DESTINATIONS
import com.simplecityapps.lift.android.ui.navigation.TopLevelNavigation

@Composable
fun LiftApp() {
    val navController = rememberNavController()

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            val topLevelNavigation = remember(navController) {
                TopLevelNavigation(navController)
            }

            if (TOP_LEVEL_DESTINATIONS
                    .map { destination -> destination.route }
                    .contains(navBackStackEntry?.destination?.route)
            ) {
                LiftBottomBar(
                    currentDestination = navBackStackEntry?.destination,
                    onNavigateToTopLevelDestination = {
                        topLevelNavigation.navigateTo(it)
                    }
                )
            }
        }
    ) { padding ->
        LiftNavHost(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
        )
    }
}
