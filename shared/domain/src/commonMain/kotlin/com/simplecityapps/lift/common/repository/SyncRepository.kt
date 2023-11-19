package com.simplecityapps.lift.common.repository

interface SyncRepository {

    suspend fun importData()
    suspend fun pushData(userId: String)
    suspend fun pullData(userId: String)
}