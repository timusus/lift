package com.simplecityapps.shuttle.common.network.model.request

import com.simplecityapps.shuttle.common.network.model.Timestamp

expect class FirestoreExerciseRequest(
     id: String,
     name: String,
     oneRepMax: Float?,
     lastUpdated: Timestamp? = null
)

