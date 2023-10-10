package com.simplecityapps.lift.android

import android.content.Context
import com.simplecityapps.lift.repository.LocalExerciseRepository
import com.simplecityapps.lift.repository.LocalRoutineRepository
import com.simplecityapps.lift.repository.LocalSessionRepository
import com.simplecityapps.shuttle.common.database.AppDataSource
import com.simplecityapps.shuttle.common.database.DatabaseHelper
import com.simplecityapps.shuttle.common.database.DriverFactory

class RoutineViewModel(val context: Context) {

    val driverFactory = DriverFactory(context)

    val databaseHelper = DatabaseHelper(driverFactory)

    val dataSource = AppDataSource(databaseHelper.database)

    val exerciseRepository = LocalExerciseRepository(
        dataSource = dataSource
    )
    val routineRepository = LocalRoutineRepository(
        dataSource = dataSource,
        exerciseRepository = exerciseRepository
    )
    val sessionRepository = LocalSessionRepository(
        dataSource = dataSource,
        routineRepository = routineRepository
    )
}