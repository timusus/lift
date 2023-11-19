package com.simplecityapps.shuttle.common.network

import com.simplecityapps.lift.common.model.Exercise
import com.simplecityapps.lift.common.model.Routine
import com.simplecityapps.lift.common.model.RoutineExercise
import com.simplecityapps.lift.common.model.Session
import com.simplecityapps.lift.common.model.SessionExercise
import com.simplecityapps.shuttle.common.network.model.ExerciseJson
import com.simplecityapps.shuttle.common.network.model.RoutineExerciseJson
import com.simplecityapps.shuttle.common.network.model.RoutineJson
import com.simplecityapps.shuttle.common.network.model.SessionExerciseJson
import com.simplecityapps.shuttle.common.network.model.SessionJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import toExercise
import toRoutine
import toRoutineExercise
import toSession
import toSessionExercise
import toUUID

class LocalJsonDataSource(private val json: Json) {

    suspend fun getExercises(): List<Exercise> {
        return withContext(Dispatchers.Default) {
            val exercisesJson = json.decodeFromString<List<ExerciseJson>>(JsonData.exercises)
                .sortedBy { exerciseJson -> exerciseJson.id }

            exercisesJson
                .map { exerciseJson -> exerciseJson.toExercise() }
        }
    }

    suspend fun getRoutineExercises(exercises: List<Exercise>): List<RoutineExercise> {
        return withContext(Dispatchers.Default) {
            val routineExercisesJson = json.decodeFromString<List<RoutineExerciseJson>>(JsonData.routineExercises)
                .sortedBy { routineExerciseJson -> routineExerciseJson.id }

            routineExercisesJson
                .map { routineExerciseJson ->
                    routineExerciseJson.toRoutineExercise(
                        exercise = exercises
                            .first { exercise -> exercise.id == routineExerciseJson.exerciseId.toUUID() }
                    )
                }
        }
    }

    suspend fun getRoutines(routineExercises: List<RoutineExercise>): List<Routine> {
        return withContext(Dispatchers.Default) {
            val routinesJson = json.decodeFromString<List<RoutineJson>>(JsonData.routines)
                .sortedBy { routineJson -> routineJson.id }

            routinesJson.map { routineJson ->
                routineJson.toRoutine(
                    exercises = routineExercises
                        .filter { routineExercise -> routineExercise.routineId == routineJson.id.toUUID() }
                )
            }
        }
    }

    suspend fun getSessionExercises(routineExercises: List<RoutineExercise>): List<SessionExercise> {
        return withContext(Dispatchers.Default) {
            val sessionExercisesJson = json.decodeFromString<List<SessionExerciseJson>>(JsonData.sessionExercises)
                .sortedBy { sessionExerciseJson -> sessionExerciseJson.id }

            sessionExercisesJson
                .map { sessionExerciseJson ->
                    sessionExerciseJson
                        .toSessionExercise(routineExercise = routineExercises
                            .first { routineExercise -> routineExercise.id == sessionExerciseJson.routineExerciseId.toUUID() })
                }
        }
    }

    suspend fun getSessions(routines: List<Routine>, sessionExercises: List<SessionExercise>): List<Session> {
        return withContext(Dispatchers.Default) {
            val sessionsJson = json.decodeFromString<List<SessionJson>>(JsonData.sessions)
                .sortedBy { sessionJson -> sessionJson.id }

            sessionsJson.map { sessionJson ->
                sessionJson.toSession(
                    routine = routines
                        .first { routine -> routine.id == sessionJson.routineId.toUUID() },
                    exercises = sessionExercises
                        .filter { sessionExercise -> sessionExercise.sessionId == sessionJson.id.toUUID() }
                )
            }
        }
    }
}