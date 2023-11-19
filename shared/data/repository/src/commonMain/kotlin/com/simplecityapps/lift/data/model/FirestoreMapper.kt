package com.simplecityapps.lift.data.model

import com.simplecityapps.shuttle.common.network.model.request.FirestoreExerciseRequest
import com.simplecityapps.shuttle.common.network.model.request.FirestoreRoutineExerciseRequest
import com.simplecityapps.shuttle.common.network.model.request.FirestoreRoutineRequest
import com.simplecityapps.shuttle.common.network.model.request.FirestoreSessionExerciseRequest
import com.simplecityapps.shuttle.common.network.model.request.FirestoreSessionRequest
import com.simplecityapps.shuttle.common.network.model.toTimestamp
import comsimplecityappslift.common.database.ExerciseEntity
import comsimplecityappslift.common.database.RoutineEntity
import comsimplecityappslift.common.database.RoutineExerciseEntity
import comsimplecityappslift.common.database.SessionEntity
import comsimplecityappslift.common.database.SessionExerciseEntity
import kotlinx.datetime.Clock

fun toFirestoreData(exerciseEntity: ExerciseEntity): FirestoreExerciseRequest {
    return FirestoreExerciseRequest(
        id = exerciseEntity.id,
        name = exerciseEntity.name,
        oneRepMax = exerciseEntity.one_rep_max,
        lastUpdated = Clock.System.now().toTimestamp()
    )
}

fun toFirestoreData(routineEntity: RoutineEntity): FirestoreRoutineRequest {
    return FirestoreRoutineRequest(
        id = routineEntity.id,
        sortOrder = routineEntity.sort_order,
        name = routineEntity.name,
        lastUpdated = Clock.System.now().toTimestamp()
    )
}

fun toFirestoreData(routineExerciseEntity: RoutineExerciseEntity): FirestoreRoutineExerciseRequest {
    return FirestoreRoutineExerciseRequest(
        id = routineExerciseEntity.id,
        sortOrder = routineExerciseEntity.sort_order,
        sets = routineExerciseEntity.sets,
        reps = routineExerciseEntity.reps,
        percentOneRepMax = routineExerciseEntity.percent_one_rep_max,
        weight = routineExerciseEntity.weight,
        routineId = routineExerciseEntity.routineId,
        exerciseId = routineExerciseEntity.exerciseId,
        lastUpdated = Clock.System.now().toTimestamp()
    )
}

fun toFirestoreData(sessionEntity: SessionEntity): FirestoreSessionRequest {
    return FirestoreSessionRequest(
        id = sessionEntity.id,
        startDate = sessionEntity.startDate.toTimestamp(),
        endDate = sessionEntity.endDate?.toTimestamp(),
        routineId = sessionEntity.routineId,
        currentExerciseId = sessionEntity.currentExerciseId,
        lastUpdated = Clock.System.now().toTimestamp()
    )
}

fun toFirestoreData(sessionExerciseEntity: SessionExerciseEntity): FirestoreSessionExerciseRequest {
    return FirestoreSessionExerciseRequest(
        id = sessionExerciseEntity.id,
        sets = sessionExerciseEntity.sets,
        reps = sessionExerciseEntity.reps,
        weight = sessionExerciseEntity.weight,
        sessionId = sessionExerciseEntity.sessionId,
        routineExerciseId = sessionExerciseEntity.routineExerciseId,
        currentSet = sessionExerciseEntity.currentSet,
        endDate = sessionExerciseEntity.endDate?.toTimestamp(),
        lastUpdated = Clock.System.now().toTimestamp()
    )
}
