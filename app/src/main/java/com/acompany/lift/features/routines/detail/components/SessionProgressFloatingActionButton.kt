package com.acompany.lift.features.routines.detail.components

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
import com.acompany.lift.features.routines.detail.data.RoutineProgress
import com.acompany.lift.features.main.data.DummyAppRepository
import java.util.*

@Composable
fun SessionProgressFloatingActionButton(
    routineProgress: RoutineProgress,
    onClick: () -> Unit
) {
    val (sessionIcon, sessionIconDescription) = when (routineProgress) {
        is RoutineProgress.None -> Icons.Rounded.Timer to "start session"
        is RoutineProgress.InProgress -> Icons.Rounded.DoubleArrow to "next session"
        is RoutineProgress.Complete -> Icons.Rounded.CheckCircle to "completed"
    }
    val date = when (routineProgress) {
        is RoutineProgress.InProgress -> routineProgress.startDate
        is RoutineProgress.Complete -> routineProgress.startDate
        else -> null
    }
    AnimatedFloatingActionButton(
        text = DateUtils.formatElapsedTime(
            elapsedTimeMillis(date ?: Date())
        ),
        icon = sessionIcon,
        contentDescription = sessionIconDescription,
        expanded = routineProgress is RoutineProgress.InProgress,
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
        routineProgress = RoutineProgress.InProgress(
            startDate = Date(),
            currentRoutineExerciseId = DummyAppRepository.routineExercises.first().id
        ),
        onClick = {}
    )
}