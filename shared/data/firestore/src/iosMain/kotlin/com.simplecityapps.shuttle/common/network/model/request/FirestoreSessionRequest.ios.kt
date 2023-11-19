package com.simplecityapps.shuttle.common.network.model.request

import com.simplecityapps.shuttle.common.network.model.Timestamp

actual class FirestoreSessionRequest actual constructor(
    id: String,
    startDate: Timestamp,
    endDate: Timestamp?,
    routineId: String,
    currentExerciseId: String?,
    lastUpdated: Timestamp?
)