package com.simplecityapps.shuttle.common.network

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

actual class RemoteDataSource(
    private val firestore: FirebaseFirestore
) {

    actual suspend fun pushExerciseEntity(userId: String, data: Any) {
        firestore.collection("users")
            .document(userId)
            .collection("Exercises")
            .add(data)
            .await()
    }

    actual suspend fun pushRoutineEntity(userId: String, data: Any) {
        firestore.collection("users")
            .document(userId)
            .collection("Routines")
            .add(data)
            .await()
    }

    actual suspend fun pushRoutineExerciseEntity(userId: String, data: Any) {
        firestore.collection("users")
            .document(userId)
            .collection("Routines")
            .document("Exercises")
            .collection("RoutineExercises")
            .add(data)
            .await()
    }

    actual suspend fun pushSessionEntity(userId: String, data: Any) {
        firestore.collection("users")
            .document(userId)
            .collection("Sessions")
            .add(data)
            .await()
    }

    actual suspend fun pushSessionExerciseEntity(userId: String, data: Any) {
        firestore.collection("users")
            .document(userId)
            .collection("Sessions")
            .document("Exercises")
            .collection("SessionExercises")
            .add(data)
            .await()
    }
}