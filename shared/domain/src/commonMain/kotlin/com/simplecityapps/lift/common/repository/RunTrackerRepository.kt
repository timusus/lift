package com.simplecityapps.lift.common.repository

import com.simplecityapps.lift.common.model.Location
import kotlinx.coroutines.flow.Flow

interface RunTrackerRepository {
    fun startSession(): Long
    fun stopSession(sessionId: Long)
    fun addLocationToSession(sessionId: Long, location: Location)
    fun getSessionLocations(sessionId: Long): Flow<List<Location>>
}