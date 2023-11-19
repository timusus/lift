package com.simplecityapps.shuttle.common.network.model.request

import com.simplecityapps.shuttle.common.network.model.Timestamp

expect class FirestoreRoutineExerciseRequest(
    id: String,
    sortOrder: Int,
    sets: Int,
    reps: Int,
    percentOneRepMax: Float?,
    weight: Float?,
    routineId: String,
    exerciseId: String,
    lastUpdated: Timestamp? = null
)

