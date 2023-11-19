package com.simplecityapps.shuttle.common.network.model

import com.google.firebase.Timestamp
import kotlinx.datetime.Instant

actual typealias Timestamp = Timestamp

actual fun Instant.toTimestamp(): Timestamp {
    return Timestamp(epochSeconds, nanosecondsOfSecond)
}

actual fun Timestamp.toInstant(): Instant {
    return Instant.fromEpochSeconds(seconds, nanoseconds)
}

actual object TimestampProvider {
    actual fun now(): Timestamp {
        return Timestamp.now()
    }
}