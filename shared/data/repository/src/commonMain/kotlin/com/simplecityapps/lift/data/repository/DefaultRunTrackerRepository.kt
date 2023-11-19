package com.simplecityapps.lift.data.repository

import com.simplecityapps.lift.common.database.AppDataSource
import com.simplecityapps.lift.common.model.Location
import com.simplecityapps.lift.common.repository.RunTrackerRepository
import com.simplecityapps.lift.data.model.toLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultRunTrackerRepository(
    private val dataSource: AppDataSource
) : RunTrackerRepository {
    override fun startSession(): String {
        return dataSource.startRunningSession()
    }

    override fun stopSession(sessionId: String) {
        dataSource.stopRunningSession(
            sessionId = sessionId
        )
    }

    override fun addLocationToSession(sessionId: String, location: Location) {
        dataSource.addLocationToSession(
            sessionId = sessionId,
            latitude = location.latitude,
            longitude = location.longitude
        )
    }

    override fun getSessionLocations(sessionId: String): Flow<List<Location>> {
        return dataSource.getSessionLocations(
            sessionId = sessionId
        ).map { runSessionLocationEntities ->
            runSessionLocationEntities
                .map { runSessionLocationEntity -> runSessionLocationEntity.toLocation() }
        }
    }
}