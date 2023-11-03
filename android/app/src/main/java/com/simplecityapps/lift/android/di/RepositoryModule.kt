package com.simplecityapps.lift.android.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.simplecityapps.lift.common.database.AppDataSource
import com.simplecityapps.lift.common.database.DatabaseHelper
import com.simplecityapps.lift.common.database.DriverFactory
import com.simplecityapps.lift.common.repository.ExerciseRepository
import com.simplecityapps.lift.common.repository.RoutineRepository
import com.simplecityapps.lift.common.repository.RunTrackerRepository
import com.simplecityapps.lift.common.repository.SessionRepository
import com.simplecityapps.lift.repository.DefaultExerciseRepository
import com.simplecityapps.lift.repository.DefaultRoutineRepository
import com.simplecityapps.lift.repository.DefaultRunTrackerRepository
import com.simplecityapps.lift.repository.DefaultSessionRepository
import com.simplecityapps.lift.repository.SessionManager
import com.simplecityapps.shuttle.common.network.RemoteDataSource
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
    fun providesRemoteDataSource(): RemoteDataSource {
        return RemoteDataSource(FirebaseFirestore.getInstance())
    }

    @Provides
    @Singleton
    fun provideExerciseRepository(
        dataSource: AppDataSource,
        remoteDataSource: RemoteDataSource
    ): ExerciseRepository {
        return DefaultExerciseRepository(
            localDataSource = dataSource,
            remoteDataSource = remoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideRoutineRepository(
        dataSource: AppDataSource,
        exerciseRepository: ExerciseRepository
    ): RoutineRepository {
        return DefaultRoutineRepository(
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
        return DefaultSessionRepository(
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
        return DefaultRunTrackerRepository(dataSource = dataSource)
    }
}
