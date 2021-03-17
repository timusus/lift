package com.acompany.lift.features.navigation

import androidx.navigation.compose.NamedNavArgument

sealed class NavDestination(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {

    object RoutineNavDestination : NavDestination(
        route = "routines/all"
    )

    class ExerciseNavDestination : NavDestination(
        route = "routines/routine/exercises"
    ) {
        companion object {
            const val ARG_ROUTINE = "routine"
        }
    }

    object SessionNavDestination : NavDestination(
        route = "sessions/all"
    )
}