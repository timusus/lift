package com.acompany.lift.features.exercises.components

import android.text.format.DateUtils
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.DoubleArrow
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acompany.lift.common.components.AnimatedFloatingActionButton
import com.acompany.lift.common.effects.elapsedTimeMillis
import com.acompany.lift.features.exercises.data.ExerciseScreenViewModel
import com.acompany.lift.features.main.data.DummyAppRepository
import java.util.*

@Composable
fun SessionProgressFloatingActionButton(
    sessionProgress: ExerciseScreenViewModel.SessionProgress,
    onClick: () -> Unit
) {
    val (sessionIcon, sessionIconDescription) = when (sessionProgress) {
        ExerciseScreenViewModel.SessionProgress.None -> Icons.Rounded.Timer to "start session"
        is ExerciseScreenViewModel.SessionProgress.InProgress -> Icons.Rounded.DoubleArrow to "next session"
        is ExerciseScreenViewModel.SessionProgress.Complete -> Icons.Rounded.CheckCircle to "completed"
    }
    AnimatedFloatingActionButton(
        text = DateUtils.formatElapsedTime(
            elapsedTimeMillis(sessionProgress.startDate ?: Date())
        ),
        icon = sessionIcon,
        contentDescription = sessionIconDescription,
        expanded = sessionProgress is ExerciseScreenViewModel.SessionProgress.InProgress,
        onClick = onClick,
        modifier = Modifier.bottomEndFabPlacement()
    )
}

fun Modifier.bottomEndFabPlacement(): Modifier {
    return this.then(
        fillMaxSize()
            .wrapContentSize(Alignment.BottomEnd)
            .padding(end = 16.dp, bottom = 16.dp)
    )
}

@Preview
@Composable
private fun SessionProgressFloatingActionButtonPreview() {
    SessionProgressFloatingActionButton(
        sessionProgress = ExerciseScreenViewModel.SessionProgress.InProgress(
            startDate = Date(),
            currentExercise = DummyAppRepository.routineExercises.first()
        ),
        onClick = {}
    )
}