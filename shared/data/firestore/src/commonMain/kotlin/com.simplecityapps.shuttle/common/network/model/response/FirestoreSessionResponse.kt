package com.simplecityapps.shuttle.common.network.model.response

import com.simplecityapps.shuttle.common.network.model.Timestamp
import com.simplecityapps.shuttle.common.network.model.TimestampProvider

/**
 * Note: Default values required for Firestore deserialization empty constructor
 */
data class FirestoreSessionResponse(
    val id: String = "",
    val startDate: Timestamp = TimestampProvider.now(),
    val endDate: Timestamp? = null,
    val routineId: String = "",
    val currentExerciseId: String? = null,
    val lastUpdated: Timestamp = TimestampProvider.now()
)