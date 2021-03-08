package com.acompany.data

import com.acompany.data.json.Exercise
import com.acompany.data.room.dao.ExerciseDao
import com.acompany.data.room.entities.fromExercises
import com.acompany.data.room.entities.toExercises
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    fun getExercises(day: Int): Flow<List<Exercise>> {
        return exerciseDao.get(day).map { it.toExercises() }
    }

    fun getAllExercises(): Flow<List<Exercise>> {
        return exerciseDao.get().map { it.toExercises() }
    }

    suspend fun insert(exercises: List<Exercise>) {
        exerciseDao.insert(exercises.fromExercises())
    }
}