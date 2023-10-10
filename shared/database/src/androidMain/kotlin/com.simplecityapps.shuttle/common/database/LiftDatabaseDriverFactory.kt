package com.simplecityapps.shuttle.common.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.simplecityapps.lift.database.LiftDatabase

actual class DriverFactory(private val context: Context) {

    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = LiftDatabase.Schema,
            context = context,
            name = "lift.db"
        ).apply {
            execute(null, "PRAGMA foreign_keys=ON;", 0)
        }
    }
}