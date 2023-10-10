package com.simplecityapps.lift.android.di

import com.simplecityapps.lift.repository.SessionRepository
import com.simplecityapps.shuttle.common.CreateSessionFromRoutineUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideCreateSessionFromRoutineUseCase(sessionRepository: SessionRepository): CreateSessionFromRoutineUseCase {
        return CreateSessionFromRoutineUseCase(sessionRepository)
    }
}
