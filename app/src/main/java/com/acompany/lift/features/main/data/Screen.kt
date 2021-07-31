package com.acompany.lift.features.main.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.StackedLineChart
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
    val title: String
) {

    abstract class RootScreen(
        route: String,
        arguments: List<NamedNavArgument> = emptyList(),
        title: String,
        val icon: ImageVector
    ) : Screen(route, arguments, title)

    object HomeScreen : RootScreen(
        route = "home",
        title = "Home",
        icon = Icons.Rounded.Home
    )

    object RoutineListScreen : RootScreen(
        route = "routines",
        title = "Routines",
        icon = Icons.Rounded.Timer
    )

    object SessionListScreen : RootScreen(
        route = "sessions",
        title = "Sessions",
        icon = Icons.Rounded.StackedLineChart
    )

    class ExerciseDetailScreen : Screen(
        route = "routines/routine/{routineId}",
        arguments = listOf(navArgument(ARG_ROUTINE) { type = NavType.LongType }),
        title = "Routines",
    ) {
        companion object {
            const val ARG_ROUTINE = "routineId"
        }
    }

    class SessionDetailScreen : Screen(
        route = "sessions/session/{sessionId}",
        arguments = listOf(navArgument(ARG_SESSION_ID) { type = NavType.LongType }),
        title = "Sessions"
    ) {
        companion object {
            const val ARG_SESSION_ID = "sessionId"
        }
    }

    companion object {
        val allScreens = listOf(
            HomeScreen,
            RoutineListScreen,
            SessionListScreen,
            ExerciseDetailScreen(),
            SessionDetailScreen()
        )

        val rootScreens = allScreens.filterIsInstance<RootScreen>()
    }
}