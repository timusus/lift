package com.acompany.lift.common.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun <T : Any> OutlinedTextField(
    modifier: Modifier = Modifier,
    key: String,
    label: String,
    initialValue: T? = null,
    keyboardType: KeyboardType,
    toString: (T?) -> String? = { t -> t?.toString() },
    fromString: (String?) -> T?,
    onValueChanged: (T?) -> Unit = {},
    onDone: () -> Unit = {}
) {
    val textState = remember(key) { mutableStateOf(toString(initialValue) ?: "") }
    OutlinedTextField(
        modifier = modifier,
        value = textState.value,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        onValueChange = { textFieldValue ->
            textState.value = textFieldValue
            onValueChanged(fromString(textFieldValue))
        },
        keyboardActions = KeyboardActions(onDone = { onDone() })
    )
}

@Composable
fun FloatTextField(
    modifier: Modifier = Modifier,
    key: String,
    label: String,
    initialValue: Float? = null,
    keyboardType: KeyboardType = KeyboardType.Number,
    onValueChanged: (Float?) -> Unit = {},
    onDone: () -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier,
        key = key,
        label = label,
        initialValue = initialValue,
        keyboardType = keyboardType,
        toString = { f -> f?.toString() },
        fromString = { s -> s?.toFloatOrNull() },
        onValueChanged = onValueChanged,
        onDone = onDone
    )
}

@Composable
fun IntTextField(
    modifier: Modifier = Modifier,
    key: String,
    label: String,
    initialValue: Int? = null,
    keyboardType: KeyboardType = KeyboardType.Number,
    onValueChanged: (Int?) -> Unit = {},
    onDone: () -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier,
        key = key,
        label = label,
        initialValue = initialValue,
        keyboardType = keyboardType,
        toString = { f -> f?.toString() },
        fromString = { s -> s?.toIntOrNull() },
        onValueChanged = onValueChanged,
        onDone = onDone
    )
}

@Composable
fun StringTextField(
    modifier: Modifier = Modifier,
    key: String,
    label: String,
    initialValue: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChanged: (String?) -> Unit = {},
    onDone: () -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier,
        key = key,
        label = label,
        initialValue = initialValue,
        keyboardType = keyboardType,
        toString = { f -> f },
        fromString = { s -> s },
        onValueChanged = onValueChanged,
        onDone = onDone
    )
}