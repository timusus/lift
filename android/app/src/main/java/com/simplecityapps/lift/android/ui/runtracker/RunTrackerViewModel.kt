package com.simplecityapps.lift.android.ui.runtracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplecityapps.lift.android.LocationRepository
import com.simplecityapps.lift.common.model.Location
import com.simplecityapps.lift.common.repository.RunTrackerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import javax.inject.Inject

@HiltViewModel
class RunTrackerViewModel @Inject constructor(
    private val runTrackerRepository: RunTrackerRepository,
    locationRepository: LocationRepository
) : ViewModel() {

    private var currentSessionId: String? = null
    private val _sessionStartTime: MutableStateFlow<Instant?> = MutableStateFlow(null)
    val sessionStartTime = _sessionStartTime.asStateFlow()

    val location = locationRepository.locationFlow

    init {
        locationRepository.locationFlow
            .onEach { location ->
                location?.let {
                    currentSessionId?.let { sessionId ->
                        runTrackerRepository.addLocationToSession(sessionId, it)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun toggleSession() {
        if (currentSessionId != null) {
            stopSession()
        } else {
            startSession()
        }
    }

    private fun startSession() {
        val sessionId = runTrackerRepository.startSession()
        _sessionStartTime.update { Clock.System.now() }
        currentSessionId = sessionId
    }

    private fun stopSession() {
        currentSessionId?.let { sessionId ->
            runTrackerRepository.stopSession(sessionId)
        }
        _sessionStartTime.update { null }
        currentSessionId = null
    }

    fun getSessionLocations(): Flow<List<Location>> {
        return currentSessionId?.let { runTrackerRepository.getSessionLocations(it) } ?: flowOf(emptyList())
    }
}