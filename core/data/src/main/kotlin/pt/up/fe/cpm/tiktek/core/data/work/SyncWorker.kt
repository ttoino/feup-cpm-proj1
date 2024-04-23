package pt.up.fe.cpm.tiktek.core.data.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.ForegroundInfo
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import timber.log.Timber

@HiltWorker
class SyncWorker
    @AssistedInject
    constructor(
        @Assisted context: Context,
        @Assisted params: WorkerParameters,
        private val syncables: Set<@JvmSuppressWildcards Syncable>,
    ) : CoroutineWorker(context, params) {
        override suspend fun doWork(): Result =
            withContext(Dispatchers.IO) {
                Timber.i("Syncing...")

                val results = (syncables.map { async { it.sync() } }).awaitAll()

                Timber.i("Synced!")

                if (results.any { it is NetworkResult.Failure }) {
                    Result.failure()
                } else if (results.any { it is NetworkResult.Error }) {
                    Result.retry()
                } else {
                    Result.success()
                }
            }

        override suspend fun getForegroundInfo(): ForegroundInfo = applicationContext.syncForegroundInfo()

        companion object {
            fun create() =
                OneTimeWorkRequestBuilder<SyncWorker>()
                    .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build(),
                    ).build()
        }
    }

fun WorkManager.enqueueSync() =
    enqueueUniqueWork(
        "sync",
        ExistingWorkPolicy.KEEP,
        SyncWorker.create(),
    )
