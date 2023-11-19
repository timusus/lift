package com.simplecityapps.lift.common.utils

import java.util.UUID

actual object UuidGenerator {
    actual fun generateUuid(): String {
        return UUID.randomUUID().toString()
    }

    actual fun generateSeededUuid(value: Long): String {
        val random = kotlin.random.Random(value)
        val mostSigBits = random.nextLong()
        val leastSigBits = random.nextLong()
        return UUID(mostSigBits, leastSigBits).toString()
    }

}