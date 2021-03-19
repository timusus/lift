package com.acompany.lift.features.exercises.data

import com.acompany.lift.data.model.Routine

sealed class ScreenState {
    object Loading : ScreenState()
    data class Ready(val routine: Routine) : ScreenState()
}