package com.simplecityapps.shuttle.common.network

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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
import com.simplecityapps.shuttle.common.network.model.toTimestamp
import kotlinx.coroutines.tasks.await
import kotlinx.datetime.Instant

actual class RemoteDataSource(
    private val firestore: FirebaseFirestore
) {

    actual suspend fun pushExerciseEntities(userId: String, exerciseRequests: List<FirestoreExerciseRequest>) {
        val batch = firestore.batch()
        exerciseRequests.forEach { exerciseRequest ->
            val documentRef = firestore.collection("Users")
                .document(userId)
                .collection("Exercises")
                .document(exerciseRequest.id)

            batch.set(documentRef, exerciseRequest)
        }
        batch.commit().await()
    }

    actual suspend fun getExerciseEntitiesAfter(userId: String, lastSynced: Instant?): List<FirestoreExerciseResponse> {
        return firestore
            .collection("Users")
            .document(userId)
            .collection("Exercises")
            .whereGreaterThan("lastUpdated", lastSynced?.toTimestamp() ?: Instant.fromEpochSeconds(0).toTimestamp())
            .orderBy("lastUpdated", Query.Direction.ASCENDING)
            .get()
            .await()
            .mapNotNull { document ->
                document.toObject(FirestoreExerciseResponse::class.java)
            }
    }

    actual suspend fun pushRoutineEntities(userId: String, routineRequests: List<FirestoreRoutineRequest>) {
        val batch = firestore.batch()
        routineRequests.forEach { routineRequest ->
            val documentRef = firestore.collection("Users")
                .document(userId)
                .collection("Routines")
                .document(routineRequest.id)

            batch.set(documentRef, routineRequest)
        }
        batch.commit().await()
    }

    actual suspend fun getRoutineEntitiesAfter(
        userId: String,
        lastSynced: Instant?
    ): List<FirestoreRoutineResponse> {
        return firestore
            .collection("Users")
            .document(userId)
            .collection("Routines")
            .whereGreaterThan("lastUpdated", lastSynced?.toTimestamp() ?: Instant.fromEpochSeconds(0).toTimestamp())
            .orderBy("lastUpdated", Query.Direction.ASCENDING)
            .get()
            .await()
            .mapNotNull { document ->
                document.toObject(FirestoreRoutineResponse::class.java)
            }
    }

    actual suspend fun pushRoutineExerciseEntities(userId: String, routineExerciseRequests: List<FirestoreRoutineExerciseRequest>) {
        val batch = firestore.batch()
        routineExerciseRequests.forEach { routineExerciseRequest ->
            val documentRef = firestore.collection("Users")
                .document(userId)
                .collection("RoutineExercises")
                .document(routineExerciseRequest.id)

            batch.set(documentRef, routineExerciseRequest)
        }
        batch.commit().await()
    }

    actual suspend fun getRoutineExerciseEntitiesAfter(
        userId: String,
        lastSynced: Instant?
    ): List<FirestoreRoutineExerciseResponse> {
        return firestore
            .collection("Users")
            .document(userId)
            .collection("RoutineExercises")
            .whereGreaterThan("lastUpdated", lastSynced?.toTimestamp() ?: Instant.fromEpochSeconds(0).toTimestamp())
            .orderBy("lastUpdated", Query.Direction.ASCENDING)
            .get()
            .await()
            .mapNotNull { document ->
                document.toObject(FirestoreRoutineExerciseResponse::class.java)
            }
    }

    actual suspend fun pushSessionEntities(userId: String, sessionRequests: List<FirestoreSessionRequest>) {
        val batch = firestore.batch()
        sessionRequests.forEach { sessionRequest ->
            val documentRef = firestore.collection("Users")
                .document(userId)
                .collection("Sessions")
                .document(sessionRequest.id)

            batch.set(documentRef, sessionRequest)
        }
        batch.commit().await()
    }

    actual suspend fun getSessionEntitiesAfter(
        userId: String,
        lastSynced: Instant?
    ): List<FirestoreSessionResponse> {
        return firestore
            .collection("Users")
            .document(userId)
            .collection("Sessions")
            .whereGreaterThan("lastUpdated", lastSynced?.toTimestamp() ?: Instant.fromEpochSeconds(0).toTimestamp())
            .orderBy("lastUpdated", Query.Direction.ASCENDING)
            .get()
            .await()
            .mapNotNull { document ->
                document.toObject(FirestoreSessionResponse::class.java)
            }
    }

    actual suspend fun pushSessionExerciseEntities(userId: String, sessionExerciseRequests: List<FirestoreSessionExerciseRequest>) {
        val batch = firestore.batch()
        sessionExerciseRequests.forEach { sessionExerciseRequest ->
            val documentRef = firestore.collection("Users")
                .document(userId)
                .collection("SessionExercises")
                .document(sessionExerciseRequest.id)

            batch.set(documentRef, sessionExerciseRequest)
        }
        batch.commit().await()
    }

    actual suspend fun getSessionExerciseEntitiesAfter(
        userId: String,
        lastSynced: Instant?
    ): List<FirestoreSessionExerciseResponse> {
        return firestore
            .collection("Users")
            .document(userId)
            .collection("SessionExercises")
            .whereGreaterThan("lastUpdated", lastSynced?.toTimestamp() ?: Instant.fromEpochSeconds(0).toTimestamp())
            .orderBy("lastUpdated", Query.Direction.ASCENDING)
            .get()
            .await()
            .mapNotNull { document ->
                document.toObject(FirestoreSessionExerciseResponse::class.java)
            }
    }

}