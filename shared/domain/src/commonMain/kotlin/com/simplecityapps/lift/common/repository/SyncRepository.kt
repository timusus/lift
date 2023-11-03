package com.simplecityapps.lift.common.repository

interface SyncRepository {

    suspend fun syncData(userId: String)
}