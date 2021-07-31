package com.acompany.lift.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.acompany.lift.RoutineProgressProto
import com.acompany.lift.features.routines.detail.data.RoutineProgress
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import java.io.InputStream
import java.io.OutputStream

object RoutineProgressSerializer : Serializer<RoutineProgressProto> {

    override val defaultValue: RoutineProgressProto = RoutineProgressProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): RoutineProgressProto {
        try {
            return RoutineProgressProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: RoutineProgressProto,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.routineProgressDataStore: DataStore<RoutineProgressProto> by dataStore(
    fileName = "routineProgress.pb",
    serializer = RoutineProgressSerializer
)

fun RoutineProgressProto.toRoutineProgress(dateAdapter: Rfc3339DateJsonAdapter): RoutineProgress {
    return when (type) {
        RoutineProgressProto.Type.None, RoutineProgressProto.Type.UNRECOGNIZED -> {
            RoutineProgress.None
        }
        RoutineProgressProto.Type.InProgress -> {
            RoutineProgress.InProgress(
                dateAdapter.fromJson(startDate)!!,
                currentExerciseId
            )
        }
        RoutineProgressProto.Type.Complete -> {
            RoutineProgress.Complete(
                dateAdapter.fromJson(startDate)!!
            )
        }
    }
}

fun RoutineProgress.toRoutineProgressProtoBuilder(dateAdapter: Rfc3339DateJsonAdapter): RoutineProgressProto.Builder {
    val builder = RoutineProgressProto.newBuilder()
    when (this) {
        RoutineProgress.None -> {
            builder.type = RoutineProgressProto.Type.None
        }
        is RoutineProgress.InProgress -> {
            builder.type = RoutineProgressProto.Type.InProgress
            builder.startDate = dateAdapter.toJson(startDate)
            builder.currentExerciseId = currentRoutineExerciseId

        }
        is RoutineProgress.Complete -> {
            builder.type = RoutineProgressProto.Type.Complete
            builder.startDate = dateAdapter.toJson(startDate)
        }
    }
    return builder
}