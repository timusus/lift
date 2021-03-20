package com.acompany.lift.features.sessions.detail.data

import android.os.Parcelable
import com.acompany.lift.data.model.Session
import kotlinx.parcelize.Parcelize

sealed class ScreenState : Parcelable {
    @Parcelize
    object Loading : ScreenState()

    @Parcelize
    class Ready(val session: Session) : ScreenState()
}