package com.acompany.lift.features.exercises.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acompany.lift.common.components.FloatTextField
import com.acompany.lift.data.model.Routine
import com.acompany.lift.data.model.RoutineExercise
import com.acompany.lift.features.exercises.data.RoutineExerciseHelper.initialWeight
import com.acompany.lift.features.main.data.DummyAppRepository

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun SheetContent(
    routine: Routine,
    routineExercise: RoutineExercise,
    onOneRepMaxChanged: (Float?) -> Unit,
    onWeightChanged: (Float?) -> Unit,
    onPercentOneRepMaxChanged: (Float?) -> Unit,
    onDoneClick: () -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(modifier = Modifier.weight(1f), text = "Edit ${routineExercise.exercise.name}")
        OutlinedButton(onClick = {
            keyboardController?.hideSoftwareKeyboard()
            onDoneClick()
        }) {
            Text(text = "Done")
        }
    }
    Spacer(modifier = Modifier.size(16.dp))

    Text(fontSize = 12.sp, text = "Routine:  ${routine.name}")
    Spacer(modifier = Modifier.size(4.dp))
    FloatTextField(
        key = routineExercise.id.toString(),
        label = "Weight",
        initialValue = routineExercise.weight ?: routineExercise.initialWeight(),
        onValueChanged = { value -> onWeightChanged(value) },
        onDone = { keyboardController?.hideSoftwareKeyboard() }
    )
    Spacer(modifier = Modifier.size(8.dp))

    FloatTextField(
        key = routineExercise.id.toString(),
        label = "% One rep max",
        initialValue = routineExercise.percentOneRepMax?.let { (it * 100) },
        onValueChanged = { value -> onPercentOneRepMaxChanged(value?.let { it / 100f }) },
        onDone = { keyboardController?.hideSoftwareKeyboard() }
    )

    Spacer(modifier = Modifier.size(24.dp))

    Text(fontSize = 12.sp, text = "All ${routineExercise.exercise.name}(s)")
    Spacer(modifier = Modifier.size(4.dp))
    FloatTextField(
        key = routineExercise.id.toString(),
        label = "One rep max",
        initialValue = routineExercise.exercise.oneRepMax,
        onValueChanged = { value -> onOneRepMaxChanged(value) },
        onDone = { keyboardController?.hideSoftwareKeyboard() }
    )
}

@Preview
@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun PreviewSheetContent() {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Expanded)
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            SheetContent(
                routine = DummyAppRepository.routines.first(),
                routineExercise = DummyAppRepository.routineExercises.first(),
                onOneRepMaxChanged = { },
                onWeightChanged = { },
                onPercentOneRepMaxChanged = { },
                onDoneClick = { })
        }) {
    }
}