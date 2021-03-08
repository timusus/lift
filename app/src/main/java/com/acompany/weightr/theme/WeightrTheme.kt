package com.acompany.weightr.theme

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import dev.chrisbanes.accompanist.insets.ExperimentalAnimatedInsets
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

@Composable
@OptIn(ExperimentalAnimatedInsets::class)
fun WeightrTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colorPalette: Colors = if (darkTheme) darkWeightrColors else lightWeightrColors,
    typography: Typography = MaterialTheme.typography,
    windowInsetsAnimationsEnabled: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = colorPalette,
        typography = typography
    ) {
        ProvideWindowInsets(windowInsetsAnimationsEnabled = windowInsetsAnimationsEnabled) {
            Crossfade(targetState = darkTheme) {
                content()
            }
        }
    }
}
