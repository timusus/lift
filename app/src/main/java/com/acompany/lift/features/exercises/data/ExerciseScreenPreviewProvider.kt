package com.acompany.lift.features.exercises.data

import android.content.Context
import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.lift.common.SoundManager
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.theme.ThemedPreviewProvider
import kotlinx.coroutines.GlobalScope
import java.util.*

class ExerciseScreenPreviewProvider : PreviewParameterProvider<Pair<Colors, ExerciseScreenViewModel>> {
    override val values: Sequence<Pair<Colors, ExerciseScreenViewModel>>
        get() = ThemedPreviewProvider().values.map { colors ->
            colors to ExerciseScreenViewModel(
                appRepository = DummyAppRepository(),
                appScope = GlobalScope,
                soundManager = SoundManager(null as Context)
            ).apply {
                this.exerciseProgressMap[DummyAppRepository.routineExercises.first()] = ExerciseScreenViewModel.ExerciseProgress.InProgress(2)
                this.sessionProgress = ExerciseScreenViewModel.SessionProgress.InProgress(
                    startDate = Date(),
                    currentExercise = DummyAppRepository.routineExercises.first()
                )
            }
        }
}