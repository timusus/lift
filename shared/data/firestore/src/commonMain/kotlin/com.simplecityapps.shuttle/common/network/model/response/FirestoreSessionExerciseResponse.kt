package com.simplecityapps.shuttle.common.network.model.response

import com.simplecityapps.shuttle.common.network.model.Timestamp
import com.simplecityapps.shuttle.common.network.model.TimestampProvider

/**
 * Note: Default values required for Firestore deserialization empty constructor
 */
data class FirestoreSessionExerciseResponse(
    val id: String = "",
    val sets: Int? = null,
    val reps: Int? = null,
    val weight: Float? = null,
    val sessionId: String = "",
    val routineExerciseId: String = "",
    val currentSet: Int = 0,
    val endDate: Timestamp? = null,
    val lastUpdated: Timestamp = TimestampProvider.now()
)