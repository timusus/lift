package com.simplecityapps.shuttle.common.network.model.response

import com.simplecityapps.shuttle.common.network.model.Timestamp
import com.simplecityapps.shuttle.common.network.model.TimestampProvider

/**
 * Note: Default values required for Firestore deserialization empty constructor
 */
data class FirestoreRoutineExerciseResponse(
    val id: String = "",
    val sortOrder: Int = 0,
    val sets: Int = 0,
    val reps: Int = 0,
    val percentOneRepMax: Float? = null,
    val weight: Float? = null,
    val routineId: String = "",
    val exerciseId: String = "",
    val lastUpdated: Timestamp = TimestampProvider.now()
)