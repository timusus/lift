package com.simplecityapps.lift.android

import com.simplecityapps.lift.common.model.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor() {

    private val _locationFlow: MutableStateFlow<Location?> = MutableStateFlow(null)
    val locationFlow: StateFlow<Location?> = _locationFlow.asStateFlow()

    fun updateLocation(location: Location?) {
        _locationFlow.update { location }
    }
}