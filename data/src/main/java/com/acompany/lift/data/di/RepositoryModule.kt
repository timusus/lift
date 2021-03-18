package com.acompany.lift.data.di

import android.content.Context
import com.acompany.lift.data.AppRepository
import com.acompany.lift.data.DatabaseHelper
import com.acompany.lift.data.LocalAppRepository
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAppDatabaseHelper(@ApplicationContext context: Context): DatabaseHelper {
        return DatabaseHelper(context)
    }

    @Provides
    @Singleton
    fun provideExerciseRepository(databaseHelper: DatabaseHelper, @IoDispatcher dispatcher: CoroutineDispatcher, jsonDateAdapter: Rfc3339DateJsonAdapter): AppRepository {
        return LocalAppRepository(databaseHelper, dispatcher, jsonDateAdapter)
    }
}