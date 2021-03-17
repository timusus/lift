package com.acompany.lift.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}