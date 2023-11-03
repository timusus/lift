package com.simplecityapps.shuttle.common.network

actual class RemoteDataSource {

    actual suspend fun pushExerciseEntity(userId: String, data: Any) {
    }

    actual suspend fun pushRoutineEntity(userId: String, data: Any) {
    }

    actual suspend fun pushRoutineExerciseEntity(userId: String, data: Any) {
    }

    actual suspend fun pushSessionEntity(userId: String, data: Any) {
    }

    actual suspend fun pushSessionExerciseEntity(userId: String, data: Any) {
    }

}