package com.acompany.lift.features.main.data

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

sealed class NavDestination(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {

    object RoutineNavDestination : NavDestination(
        route = "routines/all"
    )

    class ExerciseNavDestination : NavDestination(
        route = "routines/routine/{routineId}",
        arguments = listOf(navArgument(ARG_ROUTINE) { type = NavType.LongType })
    ) {
        companion object {
            const val ARG_ROUTINE = "routineId"
        }
    }

    object SessionNavDestination : NavDestination(
        route = "sessions/all"
    )

    class SessionDetailNavDestination : NavDestination(
        route = "sessions/session/{sessionId}",
        arguments = listOf(navArgument(ARG_SESSION_ID) { type = NavType.LongType })
    ) {
        companion object {
            const val ARG_SESSION_ID = "sessionId"
        }
    }
}