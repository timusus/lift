package com.simplecityapps.shuttle.common.network.model.request

import com.simplecityapps.shuttle.common.network.model.Timestamp

expect class FirestoreSessionRequest(
    id: String,
    startDate: Timestamp,
    endDate: Timestamp? = null,
    routineId: String,
    currentExerciseId: String? = null,
    lastUpdated: Timestamp? = null
)

