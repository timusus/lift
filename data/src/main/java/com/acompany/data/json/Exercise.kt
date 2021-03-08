package com.acompany.data.json

import androidx.room.ColumnInfo
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Exercise(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "day") val day: Int,
    @ColumnInfo(name = "sets") val sets: Int,
    @ColumnInfo(name = "reps") val reps: Int,
    @ColumnInfo(name = "percent1rm") val percentageOneRepMax: Double?,
)