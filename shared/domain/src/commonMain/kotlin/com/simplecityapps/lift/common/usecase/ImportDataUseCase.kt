package com.simplecityapps.lift.common.usecase

import com.simplecityapps.lift.common.repository.SyncRepository
import kotlin.jvm.JvmSuppressWildcards

class ImportDataUseCase(
    private val syncRepositories: Set<@JvmSuppressWildcards SyncRepository>
) {

    suspend operator fun invoke() {
        syncRepositories.forEach { it.importData() }
    }
}