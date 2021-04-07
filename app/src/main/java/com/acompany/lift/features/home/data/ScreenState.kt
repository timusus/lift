package com.acompany.lift.features.home.data

import com.acompany.lift.data.model.Session

sealed class ScreenState {
    object Loading : ScreenState()
    data class Ready(val sessions: List<Session>) : ScreenState()
}