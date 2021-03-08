package com.acompany.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.acompany.data.room.dao.ExerciseDao
import com.acompany.data.room.entities.ExerciseEntity

@Database(entities = arrayOf(ExerciseEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao

    companion object {
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "weightr"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}