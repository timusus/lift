package com.simplecityapps.lift.common.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface SettingsRepository {
    fun getLastSyncDate(): Flow<Instant?>
    suspend fun setLastSyncDate(date: Instant?)
}