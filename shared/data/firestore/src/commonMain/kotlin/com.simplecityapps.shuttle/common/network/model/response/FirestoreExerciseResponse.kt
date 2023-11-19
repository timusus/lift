package com.simplecityapps.shuttle.common.network.model.response

import com.simplecityapps.shuttle.common.network.model.Timestamp
import com.simplecityapps.shuttle.common.network.model.TimestampProvider

/**
 * Note: Default values required for Firestore deserialization empty constructor
 */
data class FirestoreExerciseResponse(
    val id: String = "",
    val name: String = "",
    val oneRepMax: Float? = null,
    val lastUpdated: Timestamp = TimestampProvider.now()
)