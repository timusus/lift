package com.simplecityapps.shuttle.common.network.model.request

import com.simplecityapps.shuttle.common.network.model.Timestamp

expect class FirestoreRoutineRequest(
    id: String,
    sortOrder: Int,
    name: String,
    lastUpdated: Timestamp? = null
)

