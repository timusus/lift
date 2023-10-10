package com.simplecityapps.lift.android

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationHelper @Inject constructor(
    @ApplicationContext val context: Context
) {
    private val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun notify(location: Location?) {
        notificationManager.notify(
            NOTIFICATION_ID,
            generateNotification(location)
        )
    }

    fun generateNotification(location: Location?): Notification {

        val mainNotificationText = location?.toString() ?: "Location Unknown"
        val titleText = context.getString(R.string.app_name)

        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID, titleText, NotificationManager.IMPORTANCE_DEFAULT
        )

        // Adds NotificationChannel to system. Attempting to create an existing notification channel with its original values performs no operation, so it's safe to perform the below sequence.
        notificationManager.createNotificationChannel(notificationChannel)

        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText(mainNotificationText)
            .setBigContentTitle(titleText)

        val launchActivityIntent = Intent(context, MainActivity::class.java)

        val cancelIntent = Intent(context, ForegroundOnlyLocationService::class.java)
        cancelIntent.putExtra(ForegroundOnlyLocationService.EXTRA_CANCEL_LOCATION_TRACKING_FROM_NOTIFICATION, true)

        val servicePendingIntent = PendingIntent.getService(
            context, 0, cancelIntent, PendingIntent.FLAG_IMMUTABLE
        )

        val activityPendingIntent = PendingIntent.getActivity(
            context, 0, launchActivityIntent, PendingIntent.FLAG_IMMUTABLE
        )

        val notificationCompatBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)

        return notificationCompatBuilder
            .setStyle(bigTextStyle)
            .setContentTitle(titleText)
            .setContentText(mainNotificationText)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setOngoing(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(
                R.drawable.ic_launcher_foreground, "Action",
                activityPendingIntent
            )
            .addAction(
                R.drawable.ic_launcher_foreground,
                "Action 2",
                servicePendingIntent
            )
            .build()
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "while_in_use_channel_01"
        const val NOTIFICATION_ID = 12345678
    }
}