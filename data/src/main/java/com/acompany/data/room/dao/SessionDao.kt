package com.acompany.data.room.dao;

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.acompany.data.room.entities.SessionAndExercisesEntity
import com.acompany.data.room.entities.SessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Query("SELECT * FROM session")
    fun get(): Flow<List<SessionEntity>>

    @Transaction
    @Query("SELECT * FROM session")
    fun getSessionsWithExercises(): Flow<List<SessionAndExercisesEntity>>

    @Insert
    suspend fun insert(sessions: List<SessionEntity>)
}