package com.acompany.weightr.features.sessions.data

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.data.model.Exercise
import com.acompany.data.model.Session
import com.acompany.weightr.theme.ThemedPreviewProvider

class SessionPreviewProvider : PreviewParameterProvider<Pair<Colors, Session>> {

    override val values: Sequence<Pair<Colors, Session>>
        get() = ThemedPreviewProvider().values.mapIndexed { index, colors ->
            colors to Session(
                id = 1,
                name = "Upper (Strength)",
                exercises = listOf(
                    Exercise(
                        name = "Squat",
                        sessionId = 0,
                        sets = index,
                        reps = index,
                        percentageOneRepMax = null
                    ),
                    Exercise(
                        name = "Deadlift",
                        sessionId = 0,
                        sets = index,
                        reps = index,
                        percentageOneRepMax = null
                    )
                )
            )
        }

}