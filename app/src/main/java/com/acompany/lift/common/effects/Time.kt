package com.acompany.lift.common.effects

import androidx.compose.runtime.*
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ticker
import java.util.*

@Composable
@OptIn(ObsoleteCoroutinesApi::class)
fun elapsedTimeMillis(
    startDate: Date,
    delayMillis: Long = 1000
): Long {
    var elapsedTimeMillis by remember { mutableStateOf(0L) }
    val ticker = ticker(delayMillis, 0)
    LaunchedEffect(ticker) {
        for (event in ticker) {
            elapsedTimeMillis = startDate.let { Date().time - startDate.time }
        }
    }
    return elapsedTimeMillis / 1000
}

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
            remainingTime = java.lang.Long.max(0, remainingTime - delayMillis)
        }
    }
    return remainingTime
}