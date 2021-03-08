package com.acompany.data.mapper

import com.acompany.data.mapper.ExerciseMapper.toExercise
import com.acompany.data.model.Exercise
import com.acompany.data.model.Session
import com.acompany.data.room.entities.SessionAndExercisesEntity
import com.acompany.data.room.entities.SessionEntity
import com.acompany.data.serialization.JsonData

object SessionMapper {

    // Room entity to model

    fun SessionAndExercisesEntity.toSession(): Session {
        return Session(
            name = session.name,
            id = session.id,
            exercises = exercises.map { exerciseEntity -> exerciseEntity.toExercise() }
        )
    }

    // Json data to model

    fun JsonData.Session.toSession(exercises: List<Exercise>): Session {
        return Session(
            name = name,
            id = id,
            exercises = exercises
        )
    }

    // Model to Room entity

    fun Session.toEntity(): SessionEntity {
        return SessionEntity(
            id = id,
            name = name
        )
    }
}