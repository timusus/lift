package com.acompany.lift.data.di

import android.content.Context
import com.acompany.lift.data.AppRepository
import com.acompany.lift.data.LocalAppRepository
import com.acompany.lift.data.Database
import com.squareup.sqldelight.android.AndroidSqliteDriver
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
    fun provideRealAppDatabase(@ApplicationContext context: Context): Database {
        return Database(AndroidSqliteDriver(Database.Schema, context, "weights.db"))
    }

    @Provides
    @Singleton
    fun provideExerciseRepository(database: Database, @IoDispatcher dispatcher: CoroutineDispatcher): AppRepository {
        return LocalAppRepository(database, dispatcher)
    }
}