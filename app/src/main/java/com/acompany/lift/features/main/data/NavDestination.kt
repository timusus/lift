package com.acompany.lift.features.main.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.StackedLineChart
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

sealed class NavDestination(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
    val icon: ImageVector,
    val contentDescription: String,
) {

    object RoutineNavDestination : NavDestination(
        route = "routines",
        icon = Icons.Rounded.Timer,
        contentDescription = "Routines",
    )

    class ExerciseNavDestination : NavDestination(
        route = "routines/routine/{routineId}",
        arguments = listOf(navArgument(ARG_ROUTINE) { type = NavType.LongType }),
        icon = Icons.Rounded.Timer,
        contentDescription = "Routines",
    ) {
        companion object {
            const val ARG_ROUTINE = "routineId"
        }
    }

    object SessionNavDestination : NavDestination(
        route = "sessions",
        icon = Icons.Rounded.StackedLineChart,
        contentDescription = "Sessions"
    )

    class SessionDetailNavDestination : NavDestination(
        route = "sessions/session/{sessionId}",
        arguments = listOf(navArgument(ARG_SESSION_ID) { type = NavType.LongType }),
        icon = Icons.Rounded.StackedLineChart,
        contentDescription = "Sessions"
    ) {
        companion object {
            const val ARG_SESSION_ID = "sessionId"
        }
    }
}