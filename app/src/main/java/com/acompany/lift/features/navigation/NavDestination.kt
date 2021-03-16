package com.acompany.lift.features.navigation

import androidx.navigation.compose.NamedNavArgument

sealed class NavDestination(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
    val name: String
) {

    object RoutineNavDestination : NavDestination(
        route = "routines/all",
        name = "Routines"
    )

    class ExerciseNavDestination : NavDestination(
        route = "routines/routine/exercises",
        name = "Routine Exercise"
    ) {
        companion object {
            const val ARG_ROUTINE = "routine"
        }
    }
}