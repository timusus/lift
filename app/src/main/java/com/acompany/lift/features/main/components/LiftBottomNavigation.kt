package com.acompany.lift.features.main.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.StackedLineChart
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.acompany.lift.common.navigation.currentRoute
import com.acompany.lift.features.main.data.NavDestination
import com.acompany.lift.theme.MaterialColors

@Composable
fun LiftBottomNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onNavigationItemClick: (LiftNavigationItem) -> Unit
) {
    val currentRoute = navController.currentRoute()
    BottomNavigation(modifier = modifier) {
        LiftNavigationItem.values().forEach { item ->
            LiftBottomNavigationItem(
                liftNavigationItem = item,
                selected = currentRoute == item.destination.route,
                onClick = {
                    onNavigationItemClick(item)
                }
            )
        }
    }
}

@Composable
fun RowScope.LiftBottomNavigationItem(
    liftNavigationItem: LiftNavigationItem,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val iconTint = with(MaterialColors) {
        if (selected) {
            if (isLight) onPrimary else primary
        } else {
            val disabledAlpha = ContentAlpha.disabled
            if (isLight) onPrimary.copy(alpha = disabledAlpha) else onSurface.copy(alpha = disabledAlpha)
        }
    }
    BottomNavigationItem(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = liftNavigationItem.icon,
                contentDescription = liftNavigationItem.contentDescription,
                tint = iconTint
            )
        }
    )
}

enum class LiftNavigationItem(
    val icon: ImageVector,
    val contentDescription: String,
    val destination: NavDestination
) {
    Routines(
        icon = Icons.Rounded.Timer,
        contentDescription = "routines",
        destination = NavDestination.RoutineNavDestination
    ),
    Sessions(
        icon = Icons.Rounded.StackedLineChart,
        contentDescription = "sessions",
        destination = NavDestination.SessionNavDestination
    )
}