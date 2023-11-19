package com.simplecityapps.lift.common.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

actual class Preferences(private val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = "settings")

    actual fun getString(key: String, defaultValue: String?): Flow<String?> {
        return context.dataStore.data
            .map { it[stringPreferencesKey(key)] ?: defaultValue }
            .distinctUntilChanged()
    }

    actual suspend fun setString(key: String, value: String?) {
        val stringKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            if (value == null) {
                preferences.remove(stringKey)
            } else {
                preferences[stringKey] = value
            }
        }
    }

    actual fun getLong(key: String, defaultValue: Long?): Flow<Long?> {
        return context.dataStore.data
            .map { it[longPreferencesKey(key)] ?: defaultValue }
            .distinctUntilChanged()
    }

    actual suspend fun setLong(key: String, value: Long?) {
        val longKey = longPreferencesKey(key)
        context.dataStore.edit { preferences ->
            if (value == null) {
                preferences.remove(longKey)
            } else {
                preferences[longKey] = value
            }
        }
    }
}