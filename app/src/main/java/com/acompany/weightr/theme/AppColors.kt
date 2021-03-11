package com.acompany.weightr.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AppColors {
    val primary = Color(0xFF38a07b)
    val secondary = Color(0xFF3a68a1)
    val background = Color(0xFFEFF0F3)
    val surface = Color(0xFFEFF0F3)

    val primaryDark = Color(0xFF38a07b)
    val secondaryDark = Color(0xFF003e72)
    val backgroundDark = Color(0xFF212121)
    val surfaceDark = Color(0xFF212121)
}

val lightAppColors = lightColors(
    primary = AppColors.primary,
    secondary = AppColors.secondary,
    background = AppColors.background,
    surface = AppColors.surface
)

val darkAppColors = darkColors(
    primary = AppColors.primaryDark,
    secondary = AppColors.secondaryDark,
    background = AppColors.backgroundDark,
    surface = AppColors.surfaceDark
)

val MaterialColors: Colors
    @Composable get() = MaterialTheme.colors