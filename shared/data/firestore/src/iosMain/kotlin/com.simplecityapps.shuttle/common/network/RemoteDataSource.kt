package com.simplecityapps.shuttle.common.network

import com.simplecityapps.shuttle.common.network.model.request.FirestoreExerciseRequest
import com.simplecityapps.shuttle.common.network.model.request.FirestoreRoutineExerciseRequest
import com.simplecityapps.shuttle.common.network.model.request.FirestoreRoutineRequest
import com.simplecityapps.shuttle.common.network.model.request.FirestoreSessionExerciseRequest
import com.simplecityapps.shuttle.common.network.model.request.FirestoreSessionRequest
import com.simplecityapps.shuttle.common.network.model.response.FirestoreExerciseResponse
import com.simplecityapps.shuttle.common.network.model.response.FirestoreRoutineExerciseResponse
import com.simplecityapps.shuttle.common.network.model.response.FirestoreRoutineResponse
import com.simplecityapps.shuttle.common.network.model.response.FirestoreSessionExerciseResponse
import com.simplecityapps.shuttle.common.network.model.response.FirestoreSessionResponse
import kotlinx.datetime.Instant

actual class RemoteDataSource {
    actual suspend fun getExerciseEntitiesAfter(
        userId: String,
        lastSynced: Instant?
    ): List<FirestoreExerciseResponse> {
        TODO("Not yet implemented")
    }

    actual suspend fun pushExerciseEntities(userId: String, exerciseRequests: FirestoreExerciseRequest) {
    }

    actual suspend fun getRoutineEntitiesAfter(
        userId: String,
        lastSynced: Instant?
    ): List<FirestoreRoutineResponse> {
        TODO("Not yet implemented")
    }

    actual suspend fun pushRoutineEntities(userId: String, routineRequests: FirestoreRoutineRequest) {
    }

    actual suspend fun getRoutineExerciseEntitiesAfter(
        userId: String,
        lastSynced: Instant?
    ): List<FirestoreRoutineExerciseResponse> {
        TODO("Not yet implemented")
    }

    actual suspend fun pushRoutineExerciseEntities(userId: String, routineExerciseRequests: FirestoreRoutineExerciseRequest) {
    }

    actual suspend fun getSessionEntitiesAfter(
        userId: String,
        lastSynced: Instant?
    ): List<FirestoreSessionResponse> {
        TODO("Not yet implemented")
    }

    actual suspend fun pushSessionEntities(userId: String, sessionRequests: FirestoreSessionRequest) {
    }

    actual suspend fun getSessionExerciseEntitiesAfter(
        userId: String,
        lastSynced: Instant?
    ): List<FirestoreSessionExerciseResponse> {
        TODO("Not yet implemented")
    }

    actual suspend fun pushSessionExerciseEntities(userId: String, sessionExerciseRequests: FirestoreSessionExerciseRequest) {
    }


}