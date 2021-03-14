package com.acompany.lift.common.components

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
    return elapsedTimeMillis
}