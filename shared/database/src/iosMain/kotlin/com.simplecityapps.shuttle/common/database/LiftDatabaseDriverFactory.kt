package com.simplecityapps.shuttle.common.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.simplecityapps.lift.database.LiftDatabase

actual class DriverFactory {

    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = LiftDatabase.Schema,
            name = "lift.db",
        ).apply {
            execute(null, "PRAGMA foreign_keys=ON;", 0)
        }
    }
}
