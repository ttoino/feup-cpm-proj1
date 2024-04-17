package pt.up.fe.cpm.tiktek.core.app

import android.app.Application
import timber.log.Timber

abstract class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
//        DynamicColors.applyToActivitiesIfAvailable(this)
        Timber.plant(Timber.DebugTree())
    }
}
