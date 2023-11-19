package com.simplecityapps.shuttle.common.network.model.response

import com.simplecityapps.shuttle.common.network.model.Timestamp
import com.simplecityapps.shuttle.common.network.model.TimestampProvider

/**
 * Note: Default values required for Firestore deserialization empty constructor
 */
data class FirestoreRoutineResponse(
    val id: String = "",
    val sortOrder: Int = 0,
    val name: String = "",
    val lastUpdated: Timestamp = TimestampProvider.now()
)