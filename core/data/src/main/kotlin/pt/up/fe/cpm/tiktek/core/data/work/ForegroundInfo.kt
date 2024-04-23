package pt.up.fe.cpm.tiktek.core.data.work

import android.app.Notification
import android.content.Context
import androidx.annotation.StringRes
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.ForegroundInfo
import pt.up.fe.cpm.tiktek.core.data.R

private const val SYNC_NOTIFICATION_ID = 0
private const val DELETE_NOTIFICATION_ID = 1

private const val BACKGROUND_NOTIFICATION_CHANNEL_ID = "BackgroundNotificationChannel"

internal fun Context.syncForegroundInfo() =
    ForegroundInfo(
        SYNC_NOTIFICATION_ID,
        notification(R.string.sync_notification_title),
    )

internal fun Context.deleteForegroundInfo() =
    ForegroundInfo(
        DELETE_NOTIFICATION_ID,
        notification(R.string.delete_notification_title),
    )

private fun Context.notification(
    @StringRes name: Int,
): Notification {
    val channel =
        NotificationChannelCompat.Builder(
            BACKGROUND_NOTIFICATION_CHANNEL_ID,
            NotificationManagerCompat.IMPORTANCE_DEFAULT,
        ).setName(getString(R.string.background_notification_channel))
            .build()

    NotificationManagerCompat.from(this).createNotificationChannel(channel)

    return NotificationCompat.Builder(this, BACKGROUND_NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_notification)
        .setContentTitle(getString(name))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()
}
