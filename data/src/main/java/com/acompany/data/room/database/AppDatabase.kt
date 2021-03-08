package com.acompany.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.acompany.data.room.dao.ExerciseDao
import com.acompany.data.room.entities.ExerciseEntity

@Database(entities = arrayOf(ExerciseEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): ExerciseDao
}