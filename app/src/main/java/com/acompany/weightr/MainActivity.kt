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
import com.acompany.data.ExerciseRepository
import com.acompany.weightr.features.components.LazyExerciseList
import com.acompany.weightr.theme.WeightrTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var repository: ExerciseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val exercises by repository.getAllExercises().collectAsState(emptyList())
            WeightrTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text(text = getString(R.string.app_name)) })
                    },
                    content = { paddingValues ->
                        LazyExerciseList(
                            exercises = exercises,
                            modifier = Modifier.padding(paddingValues)
                        ) { exercise ->
                            Timber.d("$exercise")
                        }
                    }
                )
            }
        }
    }

}