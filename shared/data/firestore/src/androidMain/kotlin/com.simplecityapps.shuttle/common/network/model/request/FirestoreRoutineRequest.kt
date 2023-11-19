package com.simplecityapps.shuttle.common.network.model.request

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

actual data class FirestoreRoutineRequest actual constructor(
    val id: String,
    val sortOrder: Int,
    val name: String,
    @ServerTimestamp val lastUpdated: Timestamp?
)