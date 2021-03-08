package com.acompany.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.acompany.data.room.dao.ExerciseDao
import com.acompany.data.room.dao.SessionDao
import com.acompany.data.room.entities.ExerciseEntity
import com.acompany.data.room.entities.SessionAndExercisesEntity
import com.acompany.data.room.entities.SessionEntity

@Database(
    entities = arrayOf(
        SessionEntity::class,
        ExerciseEntity::class
    ),
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun sessionDao(): SessionDao

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