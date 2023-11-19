package com.simplecityapps.shuttle.common.network.model.request

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

actual data class FirestoreSessionRequest actual constructor(
    val id: String,
    val startDate: Timestamp,
    val endDate: Timestamp?,
    val routineId: String,
    val currentExerciseId: String?,
    @ServerTimestamp val lastUpdated: Timestamp?
)