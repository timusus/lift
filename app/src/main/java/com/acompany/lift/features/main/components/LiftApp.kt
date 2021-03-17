package com.acompany.lift.features.main.components

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.acompany.lift.theme.AppTheme

@Composable
fun LiftApp() {
    val navController = rememberNavController()
    AppTheme {
        LiftScaffold(navController)
    }
}