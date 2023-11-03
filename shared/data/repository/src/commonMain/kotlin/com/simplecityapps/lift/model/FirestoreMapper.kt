package com.simplecityapps.lift.model

import comsimplecityappslift.common.database.ExerciseEntity
import comsimplecityappslift.common.database.RoutineEntity
import comsimplecityappslift.common.database.RoutineExerciseEntity
import comsimplecityappslift.common.database.SessionEntity
import comsimplecityappslift.common.database.SessionExerciseEntity

fun ExerciseEntity.toFirestoreData(): Map<String, Any?> {
    return hashMapOf(
        "id" to id,
        "name" to name,
        "one_rep_max" to one_rep_max,
    )
}

//fun Map<String, Any?>.toExerciseEntity(): ExerciseEntity {
//    return ExerciseEntity(
//        id = this["id"] as Long,
//        name = this["name"] as String,
//        one_rep_max = this["one_rep_max"] as Float,
//        synced = true
//    )
//}

fun RoutineEntity.toFirestoreData(): Map<String, Any?> {
    return hashMapOf(
        "id" to id,
        "sort_order" to sort_order,
        "name" to name,
    )
}

//fun Map<String, Any?>.toRoutineEntity(): RoutineEntity {
//    return RoutineEntity(
//        id = this["id"] as Long,
//        sort_order = this["sort_order"] as Int,
//        name = this["name"] as String,
//        synced = true
//    )
//}

fun RoutineExerciseEntity.toFirestoreData(): Map<String, Any?> {
    return hashMapOf(
        "id" to id,
        "sort_order" to sort_order,
        "sets" to sets,
        "reps" to reps,
        "percent_one_rep_max" to percent_one_rep_max,
        "weight" to weight,
        "routineId" to routineId,
        "exerciseId" to exerciseId,
    )
}

//fun Map<String, Any?>.toRoutineExerciseEntity(): RoutineExerciseEntity {
//    return RoutineExerciseEntity(
//        id = this["id"] as Long,
//        sort_order = this["sort_order"] as Int,
//        sets = this["sets"] as Int,
//        reps = this["reps"] as Int,
//        percent_one_rep_max = this["percent_one_rep_max"] as Float,
//        weight = this["weight"] as Float,
//        routineId = this["routineId"] as Long,
//        exerciseId = this["exerciseId"] as Long,
//        synced = true
//    )
//}

fun SessionEntity.toFirestoreData(instantMapper: InstantMapper): Map<String, Any?> {
    return hashMapOf(
        "id" to id,
        "startDate" to startDate,
        "endDate" to instantMapper.toData(endDate),
        "routineId" to routineId,
        "currentExerciseId" to currentExerciseId,
    )
}

//fun Map<String, Any?>.toSessionEntity(instantMapper: InstantMapper): SessionEntity {
//    return SessionEntity(
//        id = this["id"] as Long,
//        startDate = instantMapper.toInstant(requireNotNull(this["startDate"])),
//        endDate = instantMapper.toInstant(this["endDate"]),
//        routineId = this["routineId"] as Long,
//        currentExerciseId = this["currentExerciseId"] as Long?,
//        synced = true
//    )
//}

fun SessionExerciseEntity.toFirestoreData(instantMapper: InstantMapper): Map<String, Any?> {
    return hashMapOf(
        "id" to id,
        "sets" to sets,
        "reps" to reps,
        "weight" to weight,
        "sessionId" to sessionId,
        "routineExerciseId" to routineExerciseId,
        "currentSet" to currentSet,
        "endDate" to instantMapper.toData(endDate),
    )
}

//fun Map<String, Any?>.toSessionExerciseEntity(instantMapper: InstantMapper): SessionExerciseEntity {
//    return SessionExerciseEntity(
//        id = this["id"] as Long,
//        sets = this["sets"] as Int,
//        reps = this["reps"] as Int,
//        weight = this["weight"] as Float,
//        sessionId = this["sessionId"] as Long,
//        routineExerciseId = this["routineExerciseId"] as Long,
//        currentSet = this["currentSet"] as Int,
//        endDate = instantMapper.toInstant(this["endDate"]),
//        synced = true
//    )
//}
