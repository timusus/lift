package com.simplecityapps.lift.android.ui.sessions.detail

import android.content.res.Configuration
import android.text.format.DateUtils
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simplecityapps.lift.android.ui.fake.FakeData
import com.simplecityapps.lift.android.ui.theme.AppTheme
import com.simplecityapps.lift.common.model.SessionExercise
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.ZERO
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionExerciseRow(
    sessionExercise: SessionExercise,
    isCurrentExercise: Boolean,
    isResting: Boolean,
    restDuration: Duration?,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = sessionExercise.routineExercise.exercise.name)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "${sessionExercise.sets} sets x ${sessionExercise.reps} reps")

            (sessionExercise.weight ?: sessionExercise.routineExercise.weight)
                ?.let { weight ->
                    Text(text = "$weight kg")
                }

            if (isCurrentExercise) {
                Spacer(modifier = Modifier.size(8.dp))
                HorizontalDivider(color = DividerDefaults.color)
                Spacer(modifier = Modifier.size(16.dp))
                Row {
                    Column {
                        Text(
                            text = "Set ${sessionExercise.currentSet + 1} of ${sessionExercise.sets}",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        if (isResting) {
                            Spacer(modifier = Modifier.size(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Resting",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.primary
                                )

                                var isTextVisible by remember { mutableStateOf(true) }
                                LaunchedEffect(restDuration) {
                                    if (restDuration == ZERO) {
                                        while (true) {
                                            isTextVisible = !isTextVisible
                                            delay(500)
                                        }
                                    }
                                }

                                AnimatedVisibility(
                                    visible = isTextVisible,
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    Text(
                                        text = restDuration?.inWholeSeconds?.let { DateUtils.formatElapsedTime(it) } ?: "",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SessionExerciseRowPreview() {
    AppTheme {
        SessionExerciseRow(
            sessionExercise = FakeData.sessionExercises.first(),
            isCurrentExercise = true,
            isResting = true,
            restDuration = 3.minutes.plus(30.seconds)
        )
    }
}