package com.simplecityapps.shuttle.common.network.model

import kotlinx.datetime.Instant

actual class Timestamp

actual fun Instant.toTimestamp(): Timestamp {
    TODO()
}

actual fun Timestamp.toInstant(): Instant {
    TODO()
}

actual fun Timestamp.now(): Timestamp {
    TODO()
}
