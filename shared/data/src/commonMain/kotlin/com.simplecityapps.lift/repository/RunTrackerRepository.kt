package com.simplecityapps.lift.repository

import com.simplecityapps.lift.model.Location
import com.simplecityapps.lift.model.toLocation
import com.simplecityapps.shuttle.common.database.AppDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RunTrackerRepository(
    private val dataSource: AppDataSource
) {
    fun startSession(): Long {
        return dataSource.startRunningSession()
    }

    fun stopSession(sessionId: Long) {
        dataSource.stopRunningSession(
            sessionId = sessionId
        )
    }

    fun addLocationToSession(sessionId: Long, location: Location) {
        dataSource.addLocationToSession(
            sessionId = sessionId,
            latitude = location.latitude,
            longitude = location.longitude
        )
    }

    fun getSessionLocations(sessionId: Long): Flow<List<Location>> {
        return dataSource.getSessionLocations(
            sessionId = sessionId
        ).map { runSessionLocationEntities ->
            runSessionLocationEntities
                .map { runSessionLocationEntity -> runSessionLocationEntity.toLocation() }
        }
    }
}