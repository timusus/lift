package com.simplecityapps.lift.android.ui.sessions.detail

import com.simplecityapps.lift.common.model.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import kotlin.time.Duration

data class SessionDetailViewState(
    val session: Session,
    val restStartTime: Instant?,
    val restDuration: Flow<Duration?>
)

sealed class DialogSessionState {
    data class Show(val session: Session) : DialogSessionState()
    data object Hide : DialogSessionState()
}