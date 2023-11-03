package com.simplecityapps.lift.sync.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import com.simplecityapps.lift.common.repository.ExerciseRepository
import com.simplecityapps.lift.logging.LogPriority
import com.simplecityapps.lift.logging.asLog
import com.simplecityapps.lift.logging.logcat
import com.simplecityapps.lift.network.Dispatcher
import com.simplecityapps.lift.network.LiftDispatchers
import com.simplecityapps.lift.sync.initializers.SyncConstraints
import com.simplecityapps.lift.sync.initializers.syncForegroundInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val exerciseRepository: ExerciseRepository,
    @Dispatcher(LiftDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun getForegroundInfo(): ForegroundInfo = appContext.syncForegroundInfo()

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        val userId: String = requireNotNull(inputData.getString(ARG_USER_ID))

        try {
            exerciseRepository.syncData(userId)
            Result.success()
        } catch (e: Exception) {
            logcat(LogPriority.ERROR) { e.asLog() }
            Result.retry()
        }
    }

    companion object {
        /**
         * Expedited one time work to sync data on login
         */
        fun startUpSyncWork(userId: String) = OneTimeWorkRequestBuilder<DelegatingWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(SyncConstraints)
            .setInputData(
                SyncWorker::class.delegatedData(
                    builder = Data.Builder()
                        .putString(ARG_USER_ID, userId)
                )
            )
            .build()

        const val ARG_USER_ID = "user_id"
    }
}
