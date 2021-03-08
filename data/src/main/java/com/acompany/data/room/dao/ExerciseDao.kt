package com.acompany.data.room.dao;

import androidx.room.Dao;
import androidx.room.Insert
import androidx.room.Query;

import com.acompany.data.room.entities.ExerciseEntity;
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercise")
    fun get(): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercise where day = :day")
    fun get(day: Int): Flow<List<ExerciseEntity>>

    @Insert
    suspend fun insert(exercises: List<ExerciseEntity>)
}