package com.acompany.lift.features.main.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.acompany.lift.features.main.data.Screen
import com.acompany.lift.theme.MaterialColors

@Composable
fun LiftBottomNavigation(
    destinations: List<Screen.RootScreen>,
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
    onItemClick: (screen: Screen) -> Unit = {}
) {
    BottomNavigation(
        modifier = modifier
            .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
            .clip(CutCornerShape(topStart = 8.dp, topEnd = 4.dp, bottomStart = 4.dp, bottomEnd = 4.dp))
    ) {
        destinations.forEach { screen ->
            LiftBottomNavigationItem(
                screen = screen,
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = { onItemClick(screen) }
            )
        }
    }
}

@Composable
fun RowScope.LiftBottomNavigationItem(
    screen: Screen.RootScreen,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    BottomNavigationItem(
        modifier = modifier.semantics { contentDescription = screen.title },
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
                imageVector = screen.icon,
                contentDescription = screen.title
            )
        },
        label = {
            Text(text = screen.title)
        }
    )
}