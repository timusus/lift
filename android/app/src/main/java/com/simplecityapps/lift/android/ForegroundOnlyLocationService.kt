package com.simplecityapps.lift.android

import android.app.Service
import android.content.Intent
import android.content.res.Configuration
import android.location.Location
import android.os.Binder
import android.os.IBinder
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.simplecityapps.lift.logging.logcat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.datetime.Instant
import logcat.LogPriority
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class ForegroundOnlyLocationService : Service() {

    @Inject
    lateinit var locationRepository: LocationRepository

    @Inject
    lateinit var notificationHelper: NotificationHelper

    /*
     * Checks whether the bound activity has really gone away (foreground service with notification created) or simply orientation change (no-op).
     */
    private var configurationChange = false

    private var serviceRunningInForeground = false

    private val localBinder = LocalBinder()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var locationRequest: LocationRequest

    private lateinit var locationCallback: LocationCallback

    private var currentLocation: Location? = null

    override fun onCreate() {
        super.onCreate()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.Builder(
            TimeUnit.SECONDS.toMillis(10)
        )
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                currentLocation = locationResult.lastLocation

                locationRepository.updateLocation(currentLocation?.toLocation())

                // Updates notification content if this service is running as a foreground service.
                if (serviceRunningInForeground) {
                    notificationHelper.notify(
                        currentLocation
                    )
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val cancelLocationTrackingFromNotification = intent.getBooleanExtra(EXTRA_CANCEL_LOCATION_TRACKING_FROM_NOTIFICATION, false)

        if (cancelLocationTrackingFromNotification) {
            unsubscribeToLocationUpdates()
            stopSelf()
        }

        // Tells the system not to recreate the service after it's been killed.
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        // MainActivity (client) comes into foreground and binds to service, so the service can become a background services.
        stopForeground(STOP_FOREGROUND_REMOVE)
        serviceRunningInForeground = false
        configurationChange = false
        return localBinder
    }

    override fun onRebind(intent: Intent) {
        // MainActivity (client) returns to the foreground and rebinds to service, so the service can become a background services.
        stopForeground(STOP_FOREGROUND_REMOVE)
        serviceRunningInForeground = false
        configurationChange = false
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent): Boolean {
        // MainActivity (client) leaves foreground, so service needs to become a foreground service to maintain the 'while-in-use' label.
        // NOTE: If this method is called due to a configuration change in MainActivity, we do nothing.
        if (!configurationChange) {
            logcat { "Start foreground service" }
            val notification = notificationHelper.generateNotification(currentLocation)
            startForeground(NotificationHelper.NOTIFICATION_ID, notification)
            serviceRunningInForeground = true
        }

        // Ensures onRebind() is called if MainActivity (client) rebinds.
        return true
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        configurationChange = true
    }

    fun subscribeToLocationUpdates() {
        logcat { "subscribeToLocationUpdates()" }
        // Binding to this service doesn't actually trigger onStartCommand(). That is needed to ensure this Service can be promoted to a
        // foreground service, i.e., the service needs to be officially started (which we do here).
        startService(Intent(applicationContext, ForegroundOnlyLocationService::class.java))

        try {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.getMainLooper()
            )
        } catch (exception: SecurityException) {
            logcat(LogPriority.ERROR) { "Lost location permissions. Couldn't remove updates. $exception" }
        }
    }

    fun unsubscribeToLocationUpdates() {
        logcat { "unsubscribeToLocationUpdates()" }
        try {
            val removeTask = fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            removeTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    logcat { "Location Callback removed." }
                    stopSelf()
                } else {
                    logcat { "Failed to remove Location Callback." }
                }
            }
        } catch (exception: SecurityException) {
            logcat(LogPriority.ERROR) { "Lost location permissions. Couldn't remove updates. $exception" }
        }
    }

    /**
     * Class used for the client Binder.  Since this service runs in the same process as its
     * clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        internal val service: ForegroundOnlyLocationService
            get() = this@ForegroundOnlyLocationService
    }

    companion object {
        private const val PACKAGE_NAME = "com.example.android.whileinuselocation"

        const val EXTRA_CANCEL_LOCATION_TRACKING_FROM_NOTIFICATION = "$PACKAGE_NAME.extra.CANCEL_LOCATION_TRACKING_FROM_NOTIFICATION"
    }
}

fun Location.toLocation(): com.simplecityapps.lift.common.model.Location {
    return com.simplecityapps.lift.common.model.Location(
        timeStamp = Instant.fromEpochMilliseconds(time),
        latitude = latitude,
        longitude = longitude
    )
}