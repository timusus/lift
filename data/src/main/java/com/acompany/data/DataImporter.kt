package com.acompany.data

import android.content.Context
import com.acompany.data.json.Exercise
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.first
import timber.log.Timber

class DataImporter(
    private val context: Context,
    private val moshi: Moshi,
    private val exerciseRepository: ExerciseRepository
) {

    suspend fun import(filename: String) {
        if (exerciseRepository.getAllExercises().first().isEmpty()) {
            val type = Types.newParameterizedType(MutableList::class.java, Exercise::class.java)
            val adapter: JsonAdapter<List<Exercise>> = moshi.adapter(type)
            context.assets.open(filename).bufferedReader().use {
                adapter.fromJson(it.readText())
            }?.let { exercises ->
                exerciseRepository.insert(exercises)
                Timber.v("Data import complete. Inserted ${exercises.size} exercises")
            } ?: Timber.e("Failed to import exercises (exercises null)")
        }
    }
}