//package com.acompany.lift.datastore
//
//import android.content.Context
//import androidx.datastore.core.CorruptionException
//import androidx.datastore.core.DataStore
//import androidx.datastore.core.Serializer
//import androidx.datastore.dataStore
//import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
//import com.acompany.lift.ExerciseProgressProto
//import com.acompany.lift.features.routines.detail.data.ExerciseProgress
//import java.io.InputStream
//import java.io.OutputStream
//
//object ExerciseProgressSerializer : Serializer<ExerciseProgressProto> {
//
//    override val defaultValue: ExerciseProgressProto = ExerciseProgressProto.getDefaultInstance()
//
//    override suspend fun readFrom(input: InputStream): ExerciseProgressProto {
//        try {
//            return ExerciseProgressProto.parseFrom(input)
//        } catch (exception: InvalidProtocolBufferException) {
//            throw CorruptionException("Cannot read proto.", exception)
//        }
//    }
//
//    override suspend fun writeTo(
//        t: ExerciseProgressProto,
//        output: OutputStream
//    ) = t.writeTo(output)
//}
//
//val Context.exerciseProgressDataStore: DataStore<ExerciseProgressProto> by dataStore(
//    fileName = "exerciseProgress.pb",
//    serializer = ExerciseProgressSerializer
//)
//
//fun ExerciseProgressProto.toExerciseProgress(): ExerciseProgress {
//    return when (type) {
//        ExerciseProgressProto.Type.None, ExerciseProgressProto.Type.UNRECOGNIZED -> {
//            ExerciseProgress.None
//        }
//        ExerciseProgressProto.Type.InProgress -> {
//            ExerciseProgress.InProgress(set)
//        }
//        ExerciseProgressProto.Type.Resting -> {
//            ExerciseProgress.Resting(set)
//        }
//        ExerciseProgressProto.Type.Complete -> {
//            ExerciseProgress.Complete
//        }
//    }
//}
//
//fun ExerciseProgress.toExerciseProgressProtoBuilder(): ExerciseProgressProto.Builder {
//    val builder = ExerciseProgressProto.newBuilder()
//    when (this) {
//        ExerciseProgress.None -> {
//            builder.type = ExerciseProgressProto.Type.None
//        }
//        is ExerciseProgress.InProgress -> {
//            builder.type = ExerciseProgressProto.Type.InProgress
//            builder.set = set
//        }
//        is ExerciseProgress.Resting -> {
//            builder.type = ExerciseProgressProto.Type.Resting
//            builder.set = set
//        }
//        ExerciseProgress.Complete -> {
//            builder.type = ExerciseProgressProto.Type.Complete
//        }
//    }
//
//    return builder
//}