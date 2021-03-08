package com.acompany.weightr.di

import android.content.Context
import com.acompany.data.DataImporter
import com.acompany.data.ExerciseRepository
import com.acompany.data.room.database.AppDatabase
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun appDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.create(context)
    }

    @Provides
    fun provideExerciseRepository(database: AppDatabase): ExerciseRepository {
        return ExerciseRepository(database.exerciseDao())
    }

    @Provides
    fun provideDataImporter(@ApplicationContext context: Context, moshi: Moshi, exerciseRepository: ExerciseRepository): DataImporter {
        return DataImporter(context, moshi, exerciseRepository)
    }
}