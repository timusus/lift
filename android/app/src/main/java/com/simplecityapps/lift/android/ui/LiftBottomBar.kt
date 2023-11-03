package com.simplecityapps.lift.android.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.simplecityapps.lift.android.ui.navigation.TOP_LEVEL_DESTINATIONS
import com.simplecityapps.lift.android.ui.navigation.TopLevelDestination

@Composable
fun LiftBottomBar(
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
    onNavigateToTopLevelDestination: (TopLevelDestination) -> Unit
) {
    NavigationBar(
        modifier = modifier,
        tonalElevation = 0.dp,
    ) {
        TOP_LEVEL_DESTINATIONS.forEach { destination ->
            val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToTopLevelDestination(destination) },
                icon = {
                    Icon(
                        imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                        contentDescription = stringResource(id = destination.labelResId),
                    )
                },
                label = { Text(stringResource(destination.labelResId)) }
            )
        }
    }
}