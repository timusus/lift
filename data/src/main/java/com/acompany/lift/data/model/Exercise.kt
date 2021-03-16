package com.acompany.lift.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exercise(
    val id: Long,
    val name: String,
    val oneRepMax: Float?
) : Parcelable