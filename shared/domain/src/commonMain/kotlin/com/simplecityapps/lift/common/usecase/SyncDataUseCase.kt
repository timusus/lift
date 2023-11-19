package com.simplecityapps.lift.common.usecase

import com.simplecityapps.lift.android.common.AsyncState
import com.simplecityapps.lift.common.repository.AuthRepository
import com.simplecityapps.lift.common.repository.SyncRepository
import com.simplecityapps.lift.logging.logcat
import kotlin.jvm.JvmSuppressWildcards

class SyncDataUseCase(
    private val authRepository: AuthRepository,
    private val syncRepositories: Set<@JvmSuppressWildcards SyncRepository>
) {

    suspend operator fun invoke() {
        when (val authState = authRepository.authState.value) {
            is AsyncState.Idle -> {
                throw IllegalStateException("Sync failed, invalid authState: $authState")
            }

            is AsyncState.Loading -> {
                throw IllegalStateException("Sync failed, invalid authState: $authState")
            }

            is AsyncState.Failure -> {
                throw IllegalStateException("Sync failed, invalid authState: $authState")
            }

            is AsyncState.Success -> {
                syncRepositories.forEach { repository ->
                    logcat { "Pulling data.." }
                    repository.pullData(
                        userId = authState.value.id
                    )
                    logcat { "Pull complete. Pushing data.." }
                    repository.pushData(
                        userId = authState.value.id
                    )
                    logcat { "Push complete" }
                }
            }
        }
    }
}