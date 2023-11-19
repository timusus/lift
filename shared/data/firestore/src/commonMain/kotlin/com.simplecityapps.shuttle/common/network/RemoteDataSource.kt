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

expect class RemoteDataSource {
    suspend fun getExerciseEntitiesAfter(userId: String, lastSynced: Instant?): List<FirestoreExerciseResponse>
    suspend fun pushExerciseEntities(userId: String, exerciseRequests: List<FirestoreExerciseRequest>)
    suspend fun getRoutineEntitiesAfter(userId: String, lastSynced: Instant?): List<FirestoreRoutineResponse>
    suspend fun pushRoutineEntities(userId: String, routineRequests: List<FirestoreRoutineRequest>)
    suspend fun getRoutineExerciseEntitiesAfter(userId: String, lastSynced: Instant?): List<FirestoreRoutineExerciseResponse>
    suspend fun pushRoutineExerciseEntities(userId: String, routineExerciseRequests: List<FirestoreRoutineExerciseRequest>)
    suspend fun getSessionEntitiesAfter(userId: String, lastSynced: Instant?): List<FirestoreSessionResponse>
    suspend fun pushSessionEntities(userId: String, sessionRequests: List<FirestoreSessionRequest>)
    suspend fun getSessionExerciseEntitiesAfter(userId: String, lastSynced: Instant?): List<FirestoreSessionExerciseResponse>
    suspend fun pushSessionExerciseEntities(userId: String, sessionExerciseRequests: List<FirestoreSessionExerciseRequest>)
}