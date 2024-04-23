package pt.up.fe.cpm.tiktek.core.data.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.ForegroundInfo
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
import timber.log.Timber

@HiltWorker
class DeleteWorker
    @AssistedInject
    constructor(
        @Assisted context: Context,
        @Assisted params: WorkerParameters,
        private val deletables: Set<@JvmSuppressWildcards Deletable>,
    ) : CoroutineWorker(context, params) {
        override suspend fun doWork(): Result =
            withContext(Dispatchers.IO) {
                Timber.i("Deleting...")

                (deletables.map { async { it.delete() } }).awaitAll()

                Timber.i("Deleted!")

                Result.success()
            }

        override suspend fun getForegroundInfo(): ForegroundInfo = applicationContext.deleteForegroundInfo()

        companion object {
            fun create() =
                OneTimeWorkRequestBuilder<SyncWorker>()
                    .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                    .build()
        }
    }

fun WorkManager.enqueueDelete() =
    enqueueUniqueWork(
        "delete",
        ExistingWorkPolicy.KEEP,
        DeleteWorker.create(),
    )
