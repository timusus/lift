package com.acompany.data.serialization

import androidx.room.ColumnInfo
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class JsonData(
    @ColumnInfo(name = "sessions") val sessions: List<Session>,
    @ColumnInfo(name = "exercises") val exercises: List<Exercise>
) {

    @JsonClass(generateAdapter = true)
    data class Exercise(
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "sessionId") val sessionId: Int,
        @ColumnInfo(name = "sets") val sets: Int,
        @ColumnInfo(name = "reps") val reps: Int,
        @ColumnInfo(name = "percent1rm") val percentageOneRepMax: Double?,
    )

    @JsonClass(generateAdapter = true)
    data class Session(
        @ColumnInfo(name = "id") val id: Int,
        @ColumnInfo(name = "name") val name: String
    )
}