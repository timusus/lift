package com.acompany.lift.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.acompany.lift.ExerciseProgressProto
import com.acompany.lift.RoutineProgressProto
import com.acompany.lift.datastore.exerciseProgressDataStore
import com.acompany.lift.datastore.routineProgressDataStore
import com.acompany.lift.features.routines.detail.data.RoutineProgress
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun coroutineExceptionHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable)
        }
    }

    @Provides
    @Singleton
    @Named("AppSupervisorJob")
    fun appSupervisorJob(): Job {
        return SupervisorJob()
    }

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class AppCoroutineScope

    @Provides
    @Singleton
    @AppCoroutineScope
    fun provideAppCoroutineScope(
        @Named("AppSupervisorJob") job: Job,
        coroutineExceptionHandler: CoroutineExceptionHandler
    ): CoroutineScope {
        return CoroutineScope(Dispatchers.Main + job + coroutineExceptionHandler)
    }

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class MediumDateFormat

    @Provides
    @MediumDateFormat
    fun provideMediumDateFormat(): DateFormat {
        return SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM)
    }

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class MediumDateTimeFormat

    @Provides
    @MediumDateTimeFormat
    fun provideMediumDateTimeFormat(): DateFormat {
        return SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.MEDIUM, SimpleDateFormat.MEDIUM)
    }

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class ShortDateFormat

    @Provides
    @ShortDateFormat
    fun provideShortDateFormat(): DateFormat {
        return SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT)
    }

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class ShortDateTimeFormat

    @Provides
    @ShortDateTimeFormat
    fun provideShortDateTimeFormat(): DateFormat {
        return SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    @Provides
    @Singleton
    fun provideDatastorePreferences(@ApplicationContext context: Context, @AppCoroutineScope scope: CoroutineScope): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            scope = scope
        ) {
            context.preferencesDataStoreFile("settings")
        }
    }

    @Provides
    @Singleton
    fun provideRoutineProgressDatastore(@ApplicationContext context: Context): DataStore<RoutineProgressProto> {
        return context.routineProgressDataStore
    }

    @Provides
    @Singleton
    fun provideExerciseProgressDatastore(@ApplicationContext context: Context): DataStore<ExerciseProgressProto> {
        return context.exerciseProgressDataStore
    }
}


