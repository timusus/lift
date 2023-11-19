package com.simplecityapps.shuttle.common.network.model.request

import com.simplecityapps.shuttle.common.network.model.Timestamp

actual class FirestoreRoutineExerciseRequest actual constructor(
    id: String,
    sortOrder: Int,
    sets: Int,
    reps: Int,
    percentOneRepMax: Float?,
    weight: Float?,
    routineId: String,
    exerciseId: String,
    lastUpdated: Timestamp?
)