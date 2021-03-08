package com.acompany.weightr.startup;

import android.content.Context
import androidx.startup.Initializer
import com.acompany.data.DataImporter
import com.acompany.weightr.di.InitializerEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

/**
 * Runs at app startup, to update the database from the embedded exercises.json file if required
 */
class RepositoryInitializer : Initializer<DataImporter> {

    @Inject
    lateinit var dataImporter: DataImporter

    @Inject
    @Named("AppCoroutineScope")
    lateinit var appCoroutineScope: CoroutineScope

    override fun create(context: Context): DataImporter {

        InitializerEntryPoint.resolve(context).inject(this)

        appCoroutineScope.launch {
            dataImporter.import("data.json")
        }

        return dataImporter
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}