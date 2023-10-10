package com.simplecityapps.lift.android

import com.simplecityapps.lift.repository.ExerciseRepository
import com.simplecityapps.lift.repository.RoutineRepository
import com.simplecityapps.lift.repository.SessionRepository
import com.simplecityapps.shuttle.common.network.models.ExerciseJson
import com.simplecityapps.shuttle.common.network.models.RoutineExerciseJson
import com.simplecityapps.shuttle.common.network.models.RoutineJson
import com.simplecityapps.shuttle.common.network.models.SessionExerciseJson
import com.simplecityapps.shuttle.common.network.models.SessionJson
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DataImportUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val routineRepository: RoutineRepository,
    private val sessionRepository: SessionRepository,
    private val json: Json
) {

    suspend operator fun invoke() {
        val exercisesJson = json.decodeFromString<List<ExerciseJson>>(exercises)
            .sortedBy { exerciseJson -> exerciseJson.id }

        val exercises = exercisesJson
            .map { exerciseJson -> exerciseJson.toExercise() }

        val routineExercisesJson = json.decodeFromString<List<RoutineExerciseJson>>(routineExercises)
            .sortedBy { routineExerciseJson -> routineExerciseJson.id }

        val routineExercises = routineExercisesJson
            .map { routineExerciseJson ->
                routineExerciseJson.toRoutineExercise(
                    exercise = exercises
                        .first { exercise -> exercise.id == routineExerciseJson.exerciseId }
                )
            }

        val routinesJson = json.decodeFromString<List<RoutineJson>>(routines)
            .sortedBy { routineJson -> routineJson.id }

        val routines = routinesJson.map { routineJson ->
            routineJson.toRoutine(
                exercises = routineExercises
                    .filter { routineExercise -> routineExercise.routineId == routineJson.id }
            )
        }

        val sessionExercisesJson = json.decodeFromString<List<SessionExerciseJson>>(sessionExercises)
            .sortedBy { sessionExerciseJson -> sessionExerciseJson.id }

        val sessionExercises = sessionExercisesJson
            .map { sessionExerciseJson ->
                sessionExerciseJson
                    .toSessionExercise(routineExercise = routineExercises
                        .first { routineExercise -> routineExercise.id == sessionExerciseJson.routineExerciseId })
            }

        val sessionsJson = json.decodeFromString<List<SessionJson>>(sessions)
            .sortedBy { sessionJson -> sessionJson.id }

        val sessions = sessionsJson.map { sessionJson ->
            sessionJson.toSession(
                routine = routines
                    .first { routine -> routine.id == sessionJson.routineId },
                exercises = sessionExercises
                    .filter { sessionExercise -> sessionExercise.sessionId == sessionJson.id }
            )
        }

        exercises.forEach { exercise ->
            exerciseRepository.createExercise(
                exercise = exercise,
                generateId = false
            )
        }

        routines.forEach { routine ->
            routineRepository.createRoutine(
                routine = routine,
                generateId = false
            )
        }

        routineExercises.forEach { routineExercise ->
            routineRepository.createRoutineExercise(
                routineExercise = routineExercise,
                generateId = false
            )
        }

        sessions.forEach { session ->
            sessionRepository.createSession(
                session = session,
                generateId = false
            )
        }

        sessionExercises.forEach { sessionExerciseJson ->
            sessionRepository.createSessionExercise(
                sessionExercise = sessionExerciseJson,
                generateId = false
            )
        }
    }
}