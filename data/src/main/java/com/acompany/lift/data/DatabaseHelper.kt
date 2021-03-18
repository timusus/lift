package com.acompany.lift.data

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver

class DatabaseHelper(context: Context) {

    val database: Database by lazy {
        val currentVer = version
        if (currentVer == 0) {
            Database.Schema.create(driver)
            version = Database.Schema.version
        } else {
            val schemaVer = Database.Schema.version
            if (schemaVer > currentVer) {
                Database.Schema.migrate(driver, currentVer, schemaVer)
                version = schemaVer
            }
        }
        Database(driver)
    }

    private val driver by lazy { AndroidSqliteDriver(Database.Schema, context, "lift.db") }

    private var version: Int
        get() {
            val sqlCursor = driver.executeQuery(null, "PRAGMA user_version;", 0, null)
            return if (sqlCursor.next()) {
                sqlCursor.getLong(0)?.toInt() ?: 0
            } else {
                0
            }
        }
        set(value) {
            driver.execute(null, "PRAGMA user_version = $value;", 0, null)
        }
}