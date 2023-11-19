package com.simplecityapps.lift.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant

class DateSerializer(
    val dispatcher: CoroutineDispatcher
) {

    suspend fun serialize(value: Instant): String {
        return withContext(dispatcher) {
            value.toString()
        }
    }

    suspend fun deserialize(value: String): Instant {
        return withContext(dispatcher) {
            Instant.parse(value)
        }
    }
}