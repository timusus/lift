package com.simplecityapps.shuttle.common.network.model.request

import com.simplecityapps.shuttle.common.network.model.Timestamp

actual class FirestoreSessionExerciseRequest actual constructor(
    id: String,
    sets: Int?,
    reps: Int?,
    weight: Float?,
    sessionId: String,
    routineExerciseId: String,
    currentSet: Int,
    endDate: Timestamp?,
    lastUpdated: Timestamp?
)