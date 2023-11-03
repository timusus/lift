package com.simplecityapps.lift.repository

import com.simplecityapps.lift.common.database.AppDataSource
import com.simplecityapps.lift.common.model.Location
import com.simplecityapps.lift.common.repository.RunTrackerRepository
import com.simplecityapps.lift.model.toLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultRunTrackerRepository(
    private val dataSource: AppDataSource
) : RunTrackerRepository {
    override fun startSession(): Long {
        return dataSource.startRunningSession()
    }

    override fun stopSession(sessionId: Long) {
        dataSource.stopRunningSession(
            sessionId = sessionId
        )
    }

    override fun addLocationToSession(sessionId: Long, location: Location) {
        dataSource.addLocationToSession(
            sessionId = sessionId,
            latitude = location.latitude,
            longitude = location.longitude
        )
    }

    override fun getSessionLocations(sessionId: Long): Flow<List<Location>> {
        return dataSource.getSessionLocations(
            sessionId = sessionId
        ).map { runSessionLocationEntities ->
            runSessionLocationEntities
                .map { runSessionLocationEntity -> runSessionLocationEntity.toLocation() }
        }
    }
}