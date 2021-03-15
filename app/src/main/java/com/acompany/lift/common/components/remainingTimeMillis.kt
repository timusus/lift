package com.acompany.lift.common.components

import androidx.compose.runtime.*
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ticker
import java.lang.Long.max

@Composable
@OptIn(ObsoleteCoroutinesApi::class)
fun remainingTimeMillis(
    periodMillis: Long,
    delayMillis: Long = 1000
): Long {
    var remainingTime by remember { mutableStateOf(periodMillis) }
    val ticker = ticker(delayMillis, 1000)
    LaunchedEffect(ticker) {
        for (event in ticker) {
            remainingTime = max(0, remainingTime - delayMillis)
        }
    }
    return remainingTime
}