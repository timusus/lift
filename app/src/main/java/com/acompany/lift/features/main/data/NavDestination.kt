package com.acompany.lift.features.main.data

import androidx.navigation.compose.NamedNavArgument

sealed class NavDestination(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {

    object RoutineNavDestination : NavDestination(
        route = "routines/all"
    )

    class ExerciseNavDestination : NavDestination(
        route = "routines/routine"
    ) {
        companion object {
            const val ARG_ROUTINE = "routine"
        }
    }

    object SessionNavDestination : NavDestination(
        route = "sessions/all"
    )

    class SessionDetailNavDestination : NavDestination(
        route = "sessions/session"
    ) {
        companion object {
            const val ARG_SESSION = "session"
        }
    }
}