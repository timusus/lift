package com.simplecityapps.lift.domain.di

import com.simplecityapps.lift.common.repository.AuthRepository
import com.simplecityapps.lift.common.repository.SessionRepository
import com.simplecityapps.lift.common.repository.SyncRepository
import com.simplecityapps.lift.common.usecase.CreateSessionFromRoutineUseCase
import com.simplecityapps.lift.common.usecase.ImportDataUseCase
import com.simplecityapps.lift.common.usecase.SyncDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideCreateSessionFromRoutineUseCase(
        sessionRepository: SessionRepository
    ): CreateSessionFromRoutineUseCase {
        return CreateSessionFromRoutineUseCase(sessionRepository)
    }

    @Provides
    fun provideImportDataUseCase(
        syncRepository: Set<@JvmSuppressWildcards SyncRepository>
    ): ImportDataUseCase {
        return ImportDataUseCase(
            syncRepositories = syncRepository
        )
    }

    @Provides
    fun provideSyncDataUseCase(
        authRepository: AuthRepository,
        syncRepositories: Set<@JvmSuppressWildcards SyncRepository>
    ): SyncDataUseCase {
        return SyncDataUseCase(
            authRepository = authRepository,
            syncRepositories = syncRepositories
        )
    }
}
