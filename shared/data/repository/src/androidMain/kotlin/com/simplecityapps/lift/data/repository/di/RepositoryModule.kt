package com.simplecityapps.lift.data.repository.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.simplecityapps.lift.common.DateSerializer
import com.simplecityapps.lift.common.auth.AuthProvider
import com.simplecityapps.lift.common.auth.SignOutProvider
import com.simplecityapps.lift.common.database.AppDataSource
import com.simplecityapps.lift.common.database.DatabaseHelper
import com.simplecityapps.lift.common.database.DriverFactory
import com.simplecityapps.lift.common.database.InstantStringColumnAdapter
import com.simplecityapps.lift.common.preferences.Preferences
import com.simplecityapps.lift.common.repository.AuthRepository
import com.simplecityapps.lift.common.repository.ExerciseRepository
import com.simplecityapps.lift.common.repository.RoutineRepository
import com.simplecityapps.lift.common.repository.RunTrackerRepository
import com.simplecityapps.lift.common.repository.SessionRepository
import com.simplecityapps.lift.common.repository.SettingsRepository
import com.simplecityapps.lift.common.repository.SyncRepository
import com.simplecityapps.lift.common.utils.Dispatcher
import com.simplecityapps.lift.common.utils.LiftDispatchers
import com.simplecityapps.lift.data.repository.DefaultAuthRepository
import com.simplecityapps.lift.data.repository.DefaultExerciseRepository
import com.simplecityapps.lift.data.repository.DefaultRoutineRepository
import com.simplecityapps.lift.data.repository.DefaultRunTrackerRepository
import com.simplecityapps.lift.data.repository.DefaultSessionRepository
import com.simplecityapps.lift.data.repository.SessionManager
import com.simplecityapps.lift.data.repository.fake.DefaultSettingsRepository
import com.simplecityapps.shuttle.common.network.LocalJsonDataSource
import com.simplecityapps.shuttle.common.network.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @IntoSet
    fun bindExerciseRepository(
        exerciseRepository: ExerciseRepository
    ): SyncRepository

    @Binds
    @IntoSet
    fun bindRoutineRepository(
        routineRepository: RoutineRepository
    ): SyncRepository

    @Binds
    @IntoSet
    fun bindSessionRepository(
        sessionRepository: SessionRepository
    ): SyncRepository

    companion object {

        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): DriverFactory {
            return DriverFactory(context = context)
        }

        @Provides
        @Singleton
        fun provideInstantAdapter(dateSerializer: DateSerializer): InstantStringColumnAdapter {
            return InstantStringColumnAdapter(
                dateSerializer = dateSerializer
            )
        }

        @Provides
        @Singleton
        fun provideDatabaseHelper(
            driverFactory: DriverFactory,
            instantAdapter: InstantStringColumnAdapter
        ): DatabaseHelper {
            return DatabaseHelper(
                driverFactory = driverFactory,
                instantAdapter = instantAdapter
            )
        }

        @Provides
        @Singleton
        fun provideAppDataSource(
            databaseHelper: DatabaseHelper,
            @Dispatcher(LiftDispatchers.Default) dispatcher: CoroutineDispatcher,
            dateSerializer: DateSerializer
        ): AppDataSource {
            return AppDataSource(
                database = databaseHelper.database,
                dispatcher = dispatcher
            )
        }

        @Provides
        @Singleton
        fun providesRemoteDataSource(dateSerializer: DateSerializer): RemoteDataSource {
            return RemoteDataSource(
                firestore = FirebaseFirestore.getInstance()
            )
        }

        @Provides
        @Singleton
        fun providesLocalJsonDataSource(json: Json): LocalJsonDataSource {
            return LocalJsonDataSource(json)
        }

        @Provides
        @Singleton
        fun provideExerciseRepository(
            dataSource: AppDataSource,
            remoteDataSource: RemoteDataSource,
            localJsonDataSource: LocalJsonDataSource,
            dateSerializer: DateSerializer,
        ): ExerciseRepository {
            return DefaultExerciseRepository(
                localDataSource = dataSource,
                remoteDataSource = remoteDataSource,
                localJsonDataSource = localJsonDataSource
            )
        }

        @Provides
        @Singleton
        fun provideRoutineRepository(
            dataSource: AppDataSource,
            exerciseRepository: ExerciseRepository,
            remoteDataSource: RemoteDataSource,
            localJsonDataSource: LocalJsonDataSource,
        ): RoutineRepository {
            return DefaultRoutineRepository(
                localDataSource = dataSource,
                exerciseRepository = exerciseRepository,
                remoteDataSource = remoteDataSource,
                localJsonDataSource = localJsonDataSource,
            )
        }

        @Provides
        @Singleton
        fun provideSessionRepository(
            dataSource: AppDataSource,
            routineRepository: RoutineRepository,
            remoteDataSource: RemoteDataSource,
            localJsonDataSource: LocalJsonDataSource,
        ): SessionRepository {
            return DefaultSessionRepository(
                localDataSource = dataSource,
                routineRepository = routineRepository,
                remoteDataSource = remoteDataSource,
                localJsonDataSource = localJsonDataSource
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

        @Provides
        @Singleton
        fun provideSettingsRepository(preferences: Preferences): SettingsRepository {
            return DefaultSettingsRepository(preferences)
        }

        @Provides
        @Singleton
        fun provideAuthRepository(
            authProvider: AuthProvider,
            signOutProviders: @JvmSuppressWildcards Set<SignOutProvider>
        ): AuthRepository {
            return DefaultAuthRepository(
                authProvider = authProvider,
                signOutProviders = signOutProviders
            )
        }
    }
}
