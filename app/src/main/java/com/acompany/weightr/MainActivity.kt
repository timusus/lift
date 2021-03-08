package com.acompany.weightr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.acompany.data.WeightrRepository
import com.acompany.weightr.features.components.session.LazySessionList
import com.acompany.weightr.theme.WeightrTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var repository: WeightrRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val sessions by repository.getSessions().collectAsState(emptyList())
            WeightrTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text(text = getString(R.string.app_name)) })
                    },
                    content = { paddingValues ->
                        LazySessionList(
                            sessions = sessions,
                            modifier = Modifier.padding(paddingValues),
                        ) { session ->
                            Timber.d("$session")
                        }
                    }
                )
            }
        }
    }

}