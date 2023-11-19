package com.simplecityapps.lift.common.preferences

import kotlinx.coroutines.flow.Flow

actual class Preferences {
    actual fun getString(key: String, defaultValue: String?): Flow<String?> {
        TODO("Not yet implemented")
    }

    actual suspend fun setString(key: String, value: String?) {
    }

    actual fun getLong(key: String, defaultValue: Long?): Flow<Long?> {
        TODO("Not yet implemented")
    }

    actual suspend fun setLong(key: String, value: Long?) {
    }
}