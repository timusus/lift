package com.acompany.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.acompany.data.dao.ExerciseDao
import com.acompany.data.entities.Exercise

@Database(entities = arrayOf(Exercise::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): ExerciseDao
}