package com.acompany.weightr.theme

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.chrisbanes.accompanist.insets.ExperimentalAnimatedInsets
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

@Composable
@OptIn(ExperimentalAnimatedInsets::class)
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colorPalette: Colors = if (darkTheme) darkAppColors else lightAppColors,
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
                Surface(modifier = Modifier.fillMaxSize()) {
                    content()
                }
            }
        }
    }
}
