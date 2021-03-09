package com.acompany.weightr.features

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NamedNavArgument

open class Screen(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
    val name: String,
    val content: @Composable (PaddingValues, NavController, NavBackStackEntry) -> Unit
)