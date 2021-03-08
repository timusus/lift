package com.acompany.weightr.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CircleIcon(
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
            .size(CircleIconDefaults.PreferredIconSize)
            .clip(CircleShape)
            .background(color = tint.copy(alpha = CircleIconDefaults.IconTintAlpha))
            .then(modifier)
    )
}

object CircleIconDefaults {
    val PreferredIconSize = 46.dp
    const val IconTintAlpha = 0.2f
}