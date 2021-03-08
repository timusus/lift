package com.acompany.data

import android.content.Context
import com.acompany.data.mapper.ExerciseMapper.toExercise
import com.acompany.data.mapper.SessionMapper.toSession
import com.acompany.data.serialization.JsonData
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.first
import timber.log.Timber

class DataImporter(
    private val context: Context,
    private val moshi: Moshi,
    private val appRepository: AppRepository
) {

    suspend fun import(filename: String) {
        if (appRepository.getExercises().first().isEmpty()) {
            context.assets.open(filename).bufferedReader().use {
                moshi.adapter(JsonData::class.java).fromJson(it.readText())
            }?.let { jsonData ->
                val exercises = jsonData.exercises.map { exercise -> exercise.toExercise() }
                val sessions = jsonData.sessions.map { session -> session.toSession(exercises.filter { it.sessionId == session.id }) }
                appRepository.insert(
                    sessions = sessions,
                    exercises = exercises
                )
                Timber.v("Data import complete")
            } ?: Timber.e("Failed to import exercises (exercises null)")
        }
    }
}