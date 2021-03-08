package com.acompany.data.room.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.acompany.data.room.entities.ExerciseEntity;

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercise")
    suspend fun get(): List<ExerciseEntity>

    @Query("SELECT * FROM exercise where day = :day")
    suspend fun get(day: Int): List<ExerciseEntity>
}