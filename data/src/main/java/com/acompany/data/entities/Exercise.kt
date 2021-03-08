package com.acompany.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exercise(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "day") val day: Int,
    @ColumnInfo(name = "sets") val sets: Int,
    @ColumnInfo(name = "reps") val reps: Int,
    @ColumnInfo(name = "percent1rm") val percentageOneRepMax: Int?,
)