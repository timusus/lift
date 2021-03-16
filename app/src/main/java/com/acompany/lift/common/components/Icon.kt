package com.acompany.lift.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun Icon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current
) {
    Image(
        imageVector = icon,
        contentDescription = contentDescription,
        colorFilter = ColorFilter.tint(tint),
        modifier = Modifier
            .size(IconDefaults.PreferredIconSize)
            .then(modifier)
    )
}

object IconDefaults {
    val PreferredIconSize = 46.dp
}