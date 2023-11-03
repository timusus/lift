package com.simplecityapps.lift.android.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.StackedLineChart
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.StackedLineChart
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.simplecityapps.lift.android.R
import com.simplecityapps.lift.android.ui.runtracker.MapsDestination

class TopLevelNavigation(private val navController: NavHostController) {

    fun navigateTo(destination: TopLevelDestination) {
        navController.navigate(destination.route) {
            // Pop up to the start destination of the graph to avoid building up a large stack of destinations on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when re-selecting the same item
            launchSingleTop = true
            // Restore state when re-selecting a previously selected item
            restoreState = true
        }
    }
}

data class TopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val labelResId: Int
)

val TOP_LEVEL_DESTINATIONS = listOf(
    TopLevelDestination(
        route = MapsDestination.route,
        selectedIcon = Icons.Default.Map,
        unselectedIcon = Icons.Outlined.Map,
        labelResId = R.string.nav_maps
    ),
    TopLevelDestination(
        route = HomeDestination.route,
        selectedIcon = Icons.Default.Home,
        unselectedIcon = Icons.Outlined.Home,
        labelResId = R.string.nav_home
    ),
    TopLevelDestination(
        route = RoutineListDestination.route,
        selectedIcon = Icons.Default.Timer,
        unselectedIcon = Icons.Outlined.Timer,
        labelResId = R.string.nav_routines
    ),
    TopLevelDestination(
        route = SessionsListDestination.route,
        selectedIcon = Icons.Default.StackedLineChart,
        unselectedIcon = Icons.Outlined.StackedLineChart,
        labelResId = R.string.nav_sessions
    ),
)
