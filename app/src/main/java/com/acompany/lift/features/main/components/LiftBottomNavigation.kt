package com.acompany.lift.features.main.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.StackedLineChart
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.acompany.lift.features.main.data.NavDestination
import com.acompany.lift.theme.MaterialColors

@Composable
fun LiftBottomNavigation(
    currentRoute: String?,
    modifier: Modifier = Modifier,
    onNavigationItemClick: (LiftNavigationItem) -> Unit
) {
    BottomNavigation(
        modifier = modifier
            .padding(16.dp)
            .clip(CircleShape)
    ) {
        LiftNavigationItem.values().forEach { item ->
            LiftBottomNavigationItem(
                liftNavigationItem = item,
                selected = currentRoute?.let { currentRoute.startsWith(item.destination.route) } ?: false,
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
    BottomNavigationItem(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        alwaysShowLabel = selected,
        selectedContentColor = with(MaterialColors) {
            if (isLight) onPrimary else primary
        },
        unselectedContentColor = with(MaterialColors) {
            if (isLight) onPrimary else onSurface
        }.copy(alpha = ContentAlpha.medium),
        icon = {
            Icon(
                imageVector = liftNavigationItem.icon,
                contentDescription = liftNavigationItem.contentDescription
            )
        },
        label = {
            Text(text = liftNavigationItem.name)
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