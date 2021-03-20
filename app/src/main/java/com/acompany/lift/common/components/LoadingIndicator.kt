package com.acompany.lift.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun LoadingIndicator(
    showLoading: Boolean,
    delayMillis: Int = 5000,
    contentAlignment: Alignment = Alignment.Center
) {
    AnimatedVisibility(
        visible = showLoading,
        enter = fadeIn(animationSpec = tween(delayMillis = delayMillis))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = contentAlignment
        ) {
            CircularProgressIndicator()
        }
    }
}