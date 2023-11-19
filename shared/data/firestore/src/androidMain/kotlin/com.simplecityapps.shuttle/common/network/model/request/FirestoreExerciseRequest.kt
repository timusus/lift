package com.simplecityapps.shuttle.common.network.model.request

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

actual data class FirestoreExerciseRequest actual constructor(
    val id: String,
    val name: String,
    val oneRepMax: Float?,
    @ServerTimestamp val lastUpdated: Timestamp?
)