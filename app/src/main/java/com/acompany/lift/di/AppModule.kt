package com.acompany.lift.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import org.ocpsoft.prettytime.TimeFormat
import org.ocpsoft.prettytime.format.SimpleTimeFormat
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import javax.inject.Named
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

    @Provides
    @Singleton
    @Named("AppCoroutineScope")
    fun provideAppCoroutineScope(@Named("AppSupervisorJob") job: Job, coroutineExceptionHandler: CoroutineExceptionHandler): CoroutineScope {
        return CoroutineScope(Dispatchers.Main + job + coroutineExceptionHandler)
    }

    @Provides
    fun provideDateFormatter(): DateFormat {
        return SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.MEDIUM, SimpleDateFormat.MEDIUM)
    }
}