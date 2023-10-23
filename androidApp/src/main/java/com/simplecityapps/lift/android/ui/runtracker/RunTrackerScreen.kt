package com.simplecityapps.lift.android.ui.runtracker

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.simplecityapps.lift.android.ForegroundOnlyLocationService
import com.simplecityapps.lift.android.ui.navigation.NavigationDestination
import com.simplecityapps.lift.android.ui.theme.AppTheme
import com.simplecityapps.lift.model.Location
import com.simplecityapps.shuttle.logging.logcat
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.time.Duration
import kotlin.time.toJavaDuration

object MapsDestination : NavigationDestination {
    override val route: String = "maps_route"
    override val destination: String = "maps_destination"
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RunTrackerScreen(
    viewModel: RunTrackerViewModel = hiltViewModel()
) {
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    var showPermissionsDeniedDialog by remember { mutableStateOf(false) }
    var showPermissionsRationaleDialog by remember { mutableStateOf(false) }

    val locationService = rememberBoundLocalService<ForegroundOnlyLocationService, ForegroundOnlyLocationService.LocalBinder> {
        service
    }

    DisposableEffect(locationService) {
        onDispose { locationService?.unsubscribeToLocationUpdates() }
    }

    LaunchedEffect(locationPermissionsState, locationService) {
        if (locationPermissionsState.allPermissionsGranted) {
            logcat { "Permissions granted.. subscribing to updates" }
            locationService?.subscribeToLocationUpdates() ?: run {
                logcat { "locationService null" }
            }
        } else {
            if (locationPermissionsState.shouldShowRationale) {
                showPermissionsRationaleDialog = true
            } else {
                showPermissionsDeniedDialog = true
            }
        }
    }

    val location by viewModel.location.collectAsStateWithLifecycle()
    val sessionStartTime by viewModel.sessionStartTime.collectAsStateWithLifecycle()

    RunTrackerScreen(
        hasLocationPermissions = locationPermissionsState.allPermissionsGranted,
        location = location,
        sessionStartTime = sessionStartTime,
        onToggleSession = {
            viewModel.toggleSession()
        }
    )

    if (showPermissionsDeniedDialog) {
        AlertDialog(
            title = {
                Text("Location Permissions Required")
            },
            text = {
                Text("In order to track your run, Lift requires location access")
            },
            onDismissRequest = {
                showPermissionsDeniedDialog = false
            },
            confirmButton = {
                Button(
                    onClick = {
                        showPermissionsDeniedDialog = false
                        locationPermissionsState.launchMultiplePermissionRequest()
                    }
                ) {
                    Text(text = "Grant Permissions")
                }
            },
            dismissButton = {
                Button(onClick = { showPermissionsDeniedDialog = false }) {
                    Text(text = "Close")
                }
            }
        )
    }

    if (showPermissionsRationaleDialog) {
        AlertDialog(
            title = {
                Text("No permissions mate")
            },
            text = {
                Text(text = "We really need those permissions")
            },
            onDismissRequest = {
                showPermissionsRationaleDialog = false
            },
            confirmButton = {
                Button(
                    onClick = {
                        showPermissionsRationaleDialog = false
                        locationPermissionsState.launchMultiplePermissionRequest()
                    }
                ) {
                    Text(text = "Grant Permissions")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showPermissionsRationaleDialog = false }
                ) {
                    Text(text = "Close")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunTrackerScreen(
    hasLocationPermissions: Boolean,
    location: Location?,
    sessionStartTime: Instant?,
    onToggleSession: () -> Unit = {}
) {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Expanded,
        confirmValueChange = { _ -> false }
    )
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp)
            ) {
                if (sessionStartTime == null) {
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(),
                        onClick = { onToggleSession() }
                    ) {
                        Text("Start session")
                    }
                } else {
                    Row {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val timer by getTimerFlow(sessionStartTime).collectAsStateWithLifecycle(
                                initialValue = Duration.ZERO
                            )
                            Text(
                                text = "Duration",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Spacer(
                                modifier = Modifier.size(8.dp)
                            )
                            Text(
                                text = DateUtils.formatElapsedTime(timer.seconds),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        Spacer(
                            modifier = Modifier.size(8.dp)
                        )
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val timer by getTimerFlow(sessionStartTime).collectAsStateWithLifecycle(
                                initialValue = Duration.ZERO
                            )
                            Text(
                                text = "Distance",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                text = DateUtils.formatElapsedTime(timer.seconds),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                }
            }
        }
    ) { padding ->
        val uiSettings by remember {
            mutableStateOf(
                MapUiSettings(
                    myLocationButtonEnabled = true,
                    mapToolbarEnabled = true,
                    compassEnabled = true
                )
            )
        }
        val properties by remember {
            mutableStateOf(
                MapProperties(
                    mapType = MapType.NORMAL,
                    isMyLocationEnabled = hasLocationPermissions
                )
            )
        }

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            val topBarPadding = LocalDensity.current.run {
                WindowInsets.systemBars.getTop(this).toDp()
            }

            val cameraPositionState = rememberCameraPositionState()

            var hasUpdateLocation by remember { mutableStateOf(false) }
            LaunchedEffect(location) {
                if (!hasUpdateLocation) {
                    location?.let { location ->
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(
                            LatLng(location.latitude, location.longitude),
                            17f
                        )
                        hasUpdateLocation = true
                    }
                }
            }

            GoogleMap(
                modifier = Modifier
                    .matchParentSize()
                    .systemBarsPadding(),
                cameraPositionState = cameraPositionState,
                properties = properties,
                uiSettings = uiSettings,
                contentPadding = PaddingValues(top = topBarPadding)
            )
        }
    }
}

@Preview
@Composable
fun RunTrackerScreenPreview() {
    AppTheme {
        RunTrackerScreen(
            hasLocationPermissions = true,
            location = null,
            sessionStartTime = null
        )
    }
}


@Preview
@Composable
fun RunTrackerScreenPreview2() {
    AppTheme {
        RunTrackerScreen(
            hasLocationPermissions = true,
            location = null,
            sessionStartTime = Clock.System.now()
        )
    }
}

private fun getTimerFlow(startDate: Instant): Flow<Duration> {
    return (0..Int.MAX_VALUE)
        .asFlow()
        .map {
            Clock.System.now().minus(startDate).toJavaDuration()
        }
        .onEach { delay(1000) }
        .onStart {
            emit(Clock.System.now().minus(startDate).toJavaDuration())
        }
}