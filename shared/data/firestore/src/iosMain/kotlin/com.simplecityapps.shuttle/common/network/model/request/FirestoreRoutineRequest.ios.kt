package com.simplecityapps.shuttle.common.network.model.request

import com.simplecityapps.shuttle.common.network.model.Timestamp

actual class FirestoreRoutineRequest actual constructor(id: String, sortOrder: Int, name: String, lastUpdated: Timestamp?)