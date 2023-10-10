package com.simplecityapps.lift.android.di

import android.content.Context
import com.simplecityapps.lift.repository.ExerciseRepository
import com.simplecityapps.lift.repository.LocalExerciseRepository
import com.simplecityapps.lift.repository.LocalRoutineRepository
import com.simplecityapps.lift.repository.LocalSessionRepository
import com.simplecityapps.lift.repository.RoutineRepository
import com.simplecityapps.lift.repository.RunTrackerRepository
import com.simplecityapps.lift.repository.SessionManager
import com.simplecityapps.lift.repository.SessionRepository
import com.simplecityapps.shuttle.common.database.AppDataSource
import com.simplecityapps.shuttle.common.database.DatabaseHelper
import com.simplecityapps.shuttle.common.database.DriverFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DriverFactory {
        return DriverFactory(context = context)
    }

    @Provides
    @Singleton
    fun provideDatabaseHelper(driverFactory: DriverFactory): DatabaseHelper {
        return DatabaseHelper(driverFactory = driverFactory)
    }

    @Provides
    @Singleton
    fun provideAppDataSource(databaseHelper: DatabaseHelper): AppDataSource {
        return AppDataSource(database = databaseHelper.database)
    }

    @Provides
    @Singleton
    fun provideExerciseRepository(
        dataSource: AppDataSource
    ): ExerciseRepository {
        return LocalExerciseRepository(
            dataSource = dataSource
        )
    }

    @Provides
    @Singleton
    fun provideRoutineRepository(
        dataSource: AppDataSource,
        exerciseRepository: ExerciseRepository
    ): RoutineRepository {
        return LocalRoutineRepository(
            dataSource = dataSource,
            exerciseRepository = exerciseRepository
        )
    }

    @Provides
    @Singleton
    fun provideSessionRepository(
        dataSource: AppDataSource,
        routineRepository: RoutineRepository
    ): SessionRepository {
        return LocalSessionRepository(
            dataSource = dataSource,
            routineRepository = routineRepository
        )
    }

    @Provides
    fun provideSessionManager(sessionRepository: SessionRepository): SessionManager {
        return SessionManager(sessionRepository)
    }

    @Provides
    @Singleton
    fun provideRunTrackerRepository(dataSource: AppDataSource): RunTrackerRepository {
        return RunTrackerRepository(dataSource = dataSource)
    }
}
