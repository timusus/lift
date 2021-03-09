package com.acompany.data.mapper

import com.acompany.data.model.Exercise
import com.acompany.data.room.entities.ExerciseEntity
import com.acompany.data.serialization.JsonData

object ExerciseMapper {

    // Room entity to model

    fun ExerciseEntity.toExercise(): Exercise {
        return Exercise(
            id = uid,
            name = name,
            sessionId = sessionId,
            sets = sets,
            reps = reps,
            percentageOneRepMax = percentageOneRepMax
        )
    }

    // Json data to model

    fun JsonData.Exercise.toExercise(): Exercise {
        return Exercise(
            id = id,
            name = name,
            sessionId = sessionId,
            sets = sets,
            reps = reps,
            percentageOneRepMax = percentageOneRepMax
        )
    }

    // Model to Room entity

    fun Exercise.toEntity(): ExerciseEntity {
        return ExerciseEntity(
            uid = 0,
            name = name,
            sessionId = sessionId,
            sets = sets,
            reps = reps,
            percentageOneRepMax = percentageOneRepMax
        )
    }
}