package com.simplecityapps.lift.android.ui.sessions.detail.edit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simplecityapps.lift.android.ui.fake.FakeData
import com.simplecityapps.lift.common.model.SessionExercise

@Composable
fun SessionExerciseSheetContent(
    sessionExercise: SessionExercise,
    onOneRepMaxChanged: (Float?) -> Unit,
    onWeightChanged: (Float?) -> Unit,
    onPercentOneRepMaxChanged: (Float?) -> Unit,
    onDoneClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = 16.dp)
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Edit ${sessionExercise.routineExercise.exercise.name}"
            )
            TextButton(onClick = {
                keyboardController?.hide()
                onDoneClick()
            }) {
                Text(text = "Done")
            }
        }
        Spacer(modifier = Modifier.size(16.dp))

        Text(fontSize = 12.sp, text = "This session")
        Spacer(modifier = Modifier.size(4.dp))
        FloatTextField(
            key = sessionExercise.id.toString(),
            label = "Weight",
            initialValue = sessionExercise.weight ?: sessionExercise.routineExercise.weight,
            onValueChanged = { value -> onWeightChanged(value) },
            onDone = { keyboardController?.hide() }
        )
        Spacer(modifier = Modifier.size(8.dp))

        FloatTextField(
            key = sessionExercise.id.toString(),
            label = "% One rep max",
            initialValue = sessionExercise.routineExercise.percentOneRepMax?.let { (it * 100) },
            onValueChanged = { value -> onPercentOneRepMaxChanged(value?.let { it / 100f }) },
            onDone = { keyboardController?.hide() }
        )

        Spacer(modifier = Modifier.size(24.dp))

        Text(fontSize = 12.sp, text = "All ${sessionExercise.routineExercise.exercise.name}(s)")
        Spacer(modifier = Modifier.size(4.dp))
        FloatTextField(
            key = sessionExercise.id.toString(),
            label = "One rep max",
            initialValue = sessionExercise.routineExercise.exercise.oneRepMax,
            onValueChanged = { value -> onOneRepMaxChanged(value) },
            onDone = { keyboardController?.hide() }
        )
    }
}

@Composable
fun FloatTextField(
    key: String,
    label: String,
    initialValue: Float?,
    onValueChanged: (Float?) -> Unit,
    onDone: () -> Unit
) {
    val textState = remember(key) { mutableStateOf(initialValue?.toString() ?: "") }
    OutlinedTextField(
        value = textState.value,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        onValueChange = { textFieldValue ->
            textState.value = textFieldValue
            onValueChanged(textFieldValue.toFloatOrNull())
        },
        keyboardActions = KeyboardActions(onDone = { onDone() })
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewSheetContent() {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            skipPartiallyExpanded = true,
            initialValue = SheetValue.Expanded,
            density = LocalDensity.current
        )
    )
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            SessionExerciseSheetContent(
                sessionExercise = FakeData.sessionExercises.first(),
                onOneRepMaxChanged = { },
                onWeightChanged = { },
                onPercentOneRepMaxChanged = { }
            ) { }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize())
    }
}