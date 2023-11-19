package com.simplecityapps.lift.common.preferences

import kotlinx.coroutines.flow.Flow

expect class Preferences {
    fun getString(key: String, defaultValue: String? = null): Flow<String?>
    suspend fun setString(key: String, value: String?)
    fun getLong(key: String, defaultValue: Long? = null): Flow<Long?>
    suspend fun setLong(key: String, value: Long?)
}
