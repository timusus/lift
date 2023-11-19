package com.simplecityapps.shuttle.common.network.model.request

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

actual data class FirestoreRoutineExerciseRequest actual constructor(
    val id: String,
    val sortOrder: Int,
    val sets: Int,
    val reps: Int,
    val percentOneRepMax: Float?,
    val weight: Float?,
    val routineId: String,
    val exerciseId: String,
    @ServerTimestamp val lastUpdated: Timestamp?
)