package com.acompany.lift.common.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

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