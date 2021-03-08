package com.acompany.data

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.acompany.data.json.Exercise
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Type

class DataImporter(
    private val context: Context,
    private val moshi: Moshi,
    private val exerciseRepository: ExerciseRepository
) {

    suspend fun import(filename: String) {
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