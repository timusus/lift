package com.acompany.data

import com.acompany.data.mapper.ExerciseMapper.toEntity
import com.acompany.data.mapper.ExerciseMapper.toExercise
import com.acompany.data.mapper.SessionMapper.toEntity
import com.acompany.data.mapper.SessionMapper.toSession
import com.acompany.data.model.Exercise
import com.acompany.data.model.Session
import com.acompany.data.room.dao.ExerciseDao
import com.acompany.data.room.dao.SessionDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeightrRepository(
    private val sessionDao: SessionDao,
    private val exerciseDao: ExerciseDao
) {

    fun getSessions(): Flow<List<Session>> {
        return sessionDao.getSessionsWithExercises().map { list -> list.map { entity -> entity.toSession() } }
    }

    fun getExercises(sessionId: Int): Flow<List<Exercise>> {
        return exerciseDao.get(sessionId).map { list -> list.map { entity -> entity.toExercise() } }
    }

    fun getExercises(): Flow<List<Exercise>> {
        return exerciseDao.get().map { list -> list.map { entity -> entity.toExercise() } }
    }

    suspend fun insert(sessions: List<Session>, exercises: List<Exercise>) {
        exerciseDao.insert(exercises.map { exercise -> exercise.toEntity() })
        sessionDao.insert(sessions.map { session -> session.toEntity() })
    }
}