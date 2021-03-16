package com.acompany.lift.features.exercises.data

import androidx.compose.material.Colors
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.theme.ThemedPreviewProvider
import java.util.*

class ExerciseScreenPreviewProvider : PreviewParameterProvider<Pair<Colors, ExerciseScreenViewModel>> {
    override val values: Sequence<Pair<Colors, ExerciseScreenViewModel>>
        get() = ThemedPreviewProvider().values.map { colors ->
            colors to ExerciseScreenViewModel(DummyAppRepository()).apply {
                this.exerciseProgressMap[DummyAppRepository.routineExercises.first()] = ExerciseScreenViewModel.ExerciseProgress.InProgress(2)
                this.sessionProgress = ExerciseScreenViewModel.SessionProgress.InProgress(Date(), DummyAppRepository.routineExercises.first())
            }
        }
}