package com.acompany.data

import com.acompany.data.json.Exercise
import com.acompany.data.room.dao.ExerciseDao

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    suspend fun getExercises(day: Int) {
        exerciseDao.get(day)
    }

    suspend fun getAllExercises() {
        exerciseDao.get()
    }

    suspend fun insert(exercises: List<Exercise>) {

    }
}