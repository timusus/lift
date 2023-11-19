package com.simplecityapps.shuttle.common.network.model.request

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

actual data class FirestoreSessionExerciseRequest actual constructor(
    val id: String,
    val sets: Int?,
    val reps: Int?,
    val weight: Float?,
    val sessionId: String,
    val routineExerciseId: String,
    val currentSet: Int,
    val endDate: Timestamp?,
    @ServerTimestamp val lastUpdated: Timestamp?
)