package com.acompany.weightr.common.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun OverflowMenuButton(
    menuItems: List<PopupMenuItem>,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    iconTint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    onMenuItemClick: (PopupMenuItem) -> Unit = {}
) {
    var popupVisibility by remember { mutableStateOf(PopupMenuVisibility.Hidden) }
    IconButton(
        modifier = modifier,
        onClick = { popupVisibility = PopupMenuVisibility.Shown }
    ) {
        Icon(
            tint = iconTint,
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = contentDescription
        )
    }
    PopupMenu(
        menuItems = menuItems,
        onMenuItemClick = onMenuItemClick,
        expanded = popupVisibility == PopupMenuVisibility.Shown,
        onDismissRequest = { popupVisibility = PopupMenuVisibility.Hidden }
    )
}