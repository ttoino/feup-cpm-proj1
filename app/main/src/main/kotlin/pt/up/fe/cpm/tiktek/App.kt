package pt.up.fe.cpm.tiktek

import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import pt.up.fe.cpm.tiktek.core.app.BaseApp
import pt.up.fe.cpm.tiktek.core.data.work.enqueueSync
import javax.inject.Inject

@HiltAndroidApp
class App : BaseApp(), Configuration.Provider {
    @Inject lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory).build()

    override fun onCreate() {
        super.onCreate()

        WorkManager.getInstance(this).enqueueSync()
    }
}
