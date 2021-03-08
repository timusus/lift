package com.acompany.weightr.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object WeightrColors {
    val primary = Color(0xff435560)
    val secondary = Color(0xff6e7c7c)
    val background = Color(0xff92967d)
    val surface = Color(0xffc8c6a7)
}

val lightAppColors = lightColors(
    primary = WeightrColors.primary,
    secondary = WeightrColors.secondary,
    background = WeightrColors.background,
    surface = WeightrColors.surface
)

val darkAppColors = darkColors(
    primary = WeightrColors.primary,
    secondary = WeightrColors.secondary,
    background = WeightrColors.background,
    surface = WeightrColors.surface
)

val MaterialColors: Colors
    @Composable get() = MaterialTheme.colors