package com.simplecityapps.lift.common.repository

import com.simplecityapps.lift.common.model.Location
import kotlinx.coroutines.flow.Flow

interface RunTrackerRepository {
    fun startSession(): String
    fun stopSession(sessionId: String)
    fun addLocationToSession(sessionId: String, location: Location)
    fun getSessionLocations(sessionId: String): Flow<List<Location>>
}