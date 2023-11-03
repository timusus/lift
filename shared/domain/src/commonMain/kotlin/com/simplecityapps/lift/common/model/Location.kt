package com.simplecityapps.lift.common.model

import kotlinx.datetime.Instant

data class Location(
    val timeStamp: Instant,
    val latitude: Double,
    val longitude: Double
)
