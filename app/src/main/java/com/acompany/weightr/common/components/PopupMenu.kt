package com.acompany.weightr.common.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.acompany.weightr.theme.MaterialColors
import com.acompany.weightr.theme.MaterialTypography

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun PopupMenu(
    menuItems: List<PopupMenuItem>,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onDismissRequest: () -> Unit = {},
    onMenuItemClick: (PopupMenuItem) -> Unit
) {
    DropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        menuItems.forEach { menuItem ->
            PopMenuListItem(menuItem) {
                onMenuItemClick(menuItem)
                onDismissRequest()
            }
        }
    }
}

@Composable
private fun PopMenuListItem(
    menuItem: PopupMenuItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = menuItem.enabled,
                onClick = onClick
            )
    ) {
        Text(
            text = menuItem.title,
            color = MaterialColors.onSurface,
            style = MaterialTypography.body1,
            modifier = Modifier.padding(PopupMenuDefaults.ItemPadding)
        )
    }
}

data class PopupMenuItem(
    val title: String,
    val enabled: Boolean = true,
    val selected: Boolean = false,
)

enum class PopupMenuVisibility {
    Shown, Hidden
}

private object PopupMenuDefaults {
    val ItemPadding = 16.dp
}