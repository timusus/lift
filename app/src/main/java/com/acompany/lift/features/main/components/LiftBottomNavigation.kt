package com.acompany.lift.features.main.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.acompany.lift.features.main.data.NavDestination
import com.acompany.lift.theme.MaterialColors

@Composable
fun LiftBottomNavigation(
    currentRoute: String?,
    modifier: Modifier = Modifier,
    onNavigationItemClick: (NavDestination) -> Unit
) {
    BottomNavigation(
        modifier = modifier
            .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
            .clip(CutCornerShape(topStart = 8.dp, topEnd = 4.dp, bottomStart = 4.dp, bottomEnd = 4.dp))
    ) {
        listOf(
            NavDestination.HomeNavDestination,
            NavDestination.RoutineNavDestination,
            NavDestination.SessionNavDestination
        )
            .forEach { navDestination ->
                LiftBottomNavigationItem(
                    navDestination = navDestination,
                    selected = currentRoute?.let { currentRoute.startsWith(navDestination.route) } ?: false,
                    onClick = {
                        onNavigationItemClick(navDestination)
                    }
                )
            }
    }
}

@Composable
fun RowScope.LiftBottomNavigationItem(
    navDestination: NavDestination,
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
                imageVector = navDestination.icon,
                contentDescription = navDestination.contentDescription
            )
        },
        label = {
            Text(text = navDestination.contentDescription)
        }
    )
}