package com.simplecityapps.shuttle.common.network

expect class RemoteDataSource {
   suspend fun pushExerciseEntity(userId: String, data: Any)
   suspend fun pushRoutineEntity(userId: String, data: Any)
   suspend fun pushRoutineExerciseEntity(userId: String, data: Any)
   suspend fun pushSessionEntity(userId: String, data: Any)
   suspend fun pushSessionExerciseEntity(userId: String, data: Any)
}