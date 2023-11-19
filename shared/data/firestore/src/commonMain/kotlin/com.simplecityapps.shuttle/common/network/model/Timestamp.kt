package com.simplecityapps.shuttle.common.network.model

import kotlinx.datetime.Instant

expect class Timestamp

expect fun Instant.toTimestamp(): Timestamp

expect fun Timestamp.toInstant(): Instant

expect object TimestampProvider {
    fun now(): Timestamp
}
