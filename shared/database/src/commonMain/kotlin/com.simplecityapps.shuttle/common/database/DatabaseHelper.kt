package com.simplecityapps.shuttle.common.database

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.adapter.primitive.FloatColumnAdapter
import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import com.simplecityapps.lift.database.LiftDatabase
import comsimplecityappslift.common.database.ExerciseEntity
import comsimplecityappslift.common.database.RoutineEntity
import comsimplecityappslift.common.database.RoutineExerciseEntity
import comsimplecityappslift.common.database.RunSessionEntity
import comsimplecityappslift.common.database.RunSessionLocationEntity
import comsimplecityappslift.common.database.SessionEntity
import comsimplecityappslift.common.database.SessionExerciseEntity
import kotlinx.datetime.Instant

class DatabaseHelper(private val driverFactory: DriverFactory) {

    private val instantAdapter: ColumnAdapter<Instant, String> by lazy {
        object : ColumnAdapter<Instant, String> {
            override fun decode(databaseValue: String): Instant {
                return Instant.parse(databaseValue)
            }

            override fun encode(value: Instant): String {
                return value.toString()
            }
        }
    }

    private val sessionAdapter by lazy {
        SessionEntity.Adapter(
            instantAdapter,
            instantAdapter
        )
    }

    private val sessionExerciseAdapter by lazy {
        SessionExerciseEntity.Adapter(
            setsAdapter = IntColumnAdapter,
            repsAdapter = IntColumnAdapter,
            weightAdapter = FloatColumnAdapter,
            currentSetAdapter = IntColumnAdapter,
            endDateAdapter = instantAdapter
        )
    }

    private val routineExerciseAdapter by lazy {
        RoutineExerciseEntity.Adapter(
            sort_orderAdapter = IntColumnAdapter,
            setsAdapter = IntColumnAdapter,
            repsAdapter = IntColumnAdapter,
            percent_one_rep_maxAdapter = FloatColumnAdapter,
            weightAdapter = FloatColumnAdapter
        )
    }

    private val runSessionEntityAdapter by lazy {
        RunSessionEntity.Adapter(
            startDateAdapter = instantAdapter,
            endDateAdapter = instantAdapter
        )
    }

    private val runSessionLocationEntityAdapter by lazy {
        RunSessionLocationEntity.Adapter(
            timestampAdapter = instantAdapter
        )
    }

    private val exerciseAdapter by lazy { ExerciseEntity.Adapter(FloatColumnAdapter) }
    private val routineAdapter by lazy { RoutineEntity.Adapter(IntColumnAdapter) }

    private val driver: SqlDriver by lazy { driverFactory.createDriver() }

    val database: LiftDatabase by lazy {
        val currentVer = version
        if (currentVer == 0L) {
            LiftDatabase.Schema.create(driver)
            version = LiftDatabase.Schema.version
        } else {
            val schemaVer = LiftDatabase.Schema.version
            if (schemaVer > currentVer) {
                LiftDatabase.Schema.migrate(
                    driver = driver,
                    oldVersion = currentVer,
                    newVersion = schemaVer
                )
                version = schemaVer
            }
        }
        LiftDatabase(
            driver = driver,
            ExerciseEntityAdapter = exerciseAdapter,
            RoutineEntityAdapter = routineAdapter,
            RoutineExerciseEntityAdapter = routineExerciseAdapter,
            SessionEntityAdapter = sessionAdapter,
            SessionExerciseEntityAdapter = sessionExerciseAdapter,
            RunSessionEntityAdapter = runSessionEntityAdapter,
            RunSessionLocationEntityAdapter = runSessionLocationEntityAdapter
        )
    }

    private var version: Long
        get() {
            return driver.executeQuery(
                identifier = null,
                sql = "PRAGMA user_version;",
                mapper = { cursor ->
                    check(cursor.next().value)
                    QueryResult.Value(cursor.getLong(0) ?: 0L)
                },
                parameters = 0
            ).value
        }
        set(value) {
            driver.execute(null, "PRAGMA user_version = $value;", 0, null)
        }
}