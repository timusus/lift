package com.acompany.data.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.acompany.data.entities.Exercise;

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercise")
    suspend fun get(): List<Exercise>

    @Query("SELECT * FROM exercise where day = :day")
    suspend fun get(day: Int): List<Exercise>
}