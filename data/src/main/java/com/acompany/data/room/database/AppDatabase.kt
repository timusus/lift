package com.acompany.data.room.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.acompany.data.ExerciseRepository
import com.acompany.data.json.Exercise
import com.acompany.data.room.dao.ExerciseDao
import com.acompany.data.room.entities.ExerciseEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Type

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

        suspend fun populate(context: Context, moshi: Moshi, exerciseRepository: ExerciseRepository, filename: String) {
            withContext(Dispatchers.IO) {
                val type: Type = Types.newParameterizedType(MutableList::class.java, Exercise::class.java)
                val adapter: JsonAdapter<List<Exercise>> = moshi.adapter(type)
                context.assets.open(filename).bufferedReader().use {
                    adapter.fromJson(it.readText())
                }?.let { exercises ->
                    exerciseRepository.insert(exercises)
                } ?: Log.e("DataImporter", "Failed to import exercises (exercises null)")
            }
        }
    }
}