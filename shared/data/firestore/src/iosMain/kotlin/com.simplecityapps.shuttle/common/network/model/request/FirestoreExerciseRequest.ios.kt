package com.simplecityapps.shuttle.common.network.model.request

import com.simplecityapps.shuttle.common.network.model.Timestamp

actual class FirestoreExerciseRequest actual constructor(
    id: String,
    name: String,
    oneRepMax: Float?,
    lastUpdated: Timestamp?
)