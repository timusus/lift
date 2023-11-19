package com.simplecityapps.lift.data.repository.fake

import com.simplecityapps.lift.common.preferences.Preferences
import com.simplecityapps.lift.common.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant

enum class PreferenceKey(val value: String) {
    LastSyncDate("last_sync_Data")
}

class DefaultSettingsRepository(
    private val preferences: Preferences
) : SettingsRepository {

    override fun getLastSyncDate(): Flow<Instant?> {
        return preferences.getLong(PreferenceKey.LastSyncDate.value)
            .map { millis -> millis?.let { Instant.fromEpochMilliseconds(millis) } }
    }

    override suspend fun setLastSyncDate(date: Instant?) {
        preferences.setLong(PreferenceKey.LastSyncDate.value, date?.toEpochMilliseconds())
    }
}