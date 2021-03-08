package com.acompany.data.room.entities

import androidx.room.Embedded
import androidx.room.Relation

data class SessionAndExercisesEntity(
    @Embedded val session: SessionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "sessionId"
    )
    val exercises: List<ExerciseEntity>
)