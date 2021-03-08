package com.acompany.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.acompany.data.json.Exercise

@Entity(tableName = "exercise")
data class ExerciseEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "day") val day: Int,
    @ColumnInfo(name = "sets") val sets: Int,
    @ColumnInfo(name = "reps") val reps: Int,
    @ColumnInfo(name = "percent1rm") val percentageOneRepMax: Double?,
)

fun ExerciseEntity.toExercise(): Exercise {
    return Exercise(
        name = name,
        day = day,
        sets = sets,
        reps = reps,
        percentageOneRepMax = percentageOneRepMax
    )
}

fun List<ExerciseEntity>.toExercises(): List<Exercise> {
    return map { exerciseEntity -> exerciseEntity.toExercise() }
}

fun Exercise.fromExercise(): ExerciseEntity {
    return ExerciseEntity(
        uid = 0,
        name = name,
        day = day,
        sets = sets,
        reps = reps,
        percentageOneRepMax = percentageOneRepMax
    )
}

fun List<Exercise>.fromExercises(): List<ExerciseEntity> {
    return map { exercise -> exercise.fromExercise() }
}