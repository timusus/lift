package com.acompany.lift.theme

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
    val onBackground = Color(0xFF212121)
    val surface = Color(0xFFEFF0F3)

    val backgroundDark = Color(0xFF212121)
    val onBackgroundDark = Color(0xFFEFF0F3)
    val surfaceDark = Color(0xFF212121)
}

val lightAppColors = lightColors(
    primary = AppColors.primary,
    onPrimary = Color.White,
    secondary = AppColors.secondary,
    onSecondary = Color.White,
    background = AppColors.background,
    onBackground = AppColors.onBackground,
    surface = AppColors.surface
)

val darkAppColors = darkColors(
    primary = AppColors.primary,
    onPrimary = Color.White,
    secondary = AppColors.secondary,
    onSecondary = Color.White,
    background = AppColors.backgroundDark,
    onBackground = AppColors.onBackgroundDark,
    surface = AppColors.surfaceDark
)

val MaterialColors: Colors
    @Composable get() = MaterialTheme.colors