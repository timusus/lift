package com.acompany.lift.features.main.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate

@Composable
fun LiftScaffold(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        content = { paddingValues ->
            Box(Modifier.padding(paddingValues)) {
                ListNavHost(navController)
            }
        },
        bottomBar = {
            LiftBottomNavigation(navController) { item ->
                navController.navigate(item.destination.route)
            }
        }
    )
}