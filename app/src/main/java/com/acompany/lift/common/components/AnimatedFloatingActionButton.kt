package com.acompany.lift.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun AnimatedFloatingActionButton(
    text: String,
    icon: ImageVector,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AnimatedVisibility(visible = expanded) {
                Box(Modifier.padding(start = 16.dp, end = 8.dp)) {
                    Text(text = text)
                }
            }
            Icon(
                imageVector = icon,
                contentDescription = contentDescription
            )
            AnimatedVisibility(visible = expanded) {
                Spacer(Modifier.width(20.dp))
            }
        }
    }
}