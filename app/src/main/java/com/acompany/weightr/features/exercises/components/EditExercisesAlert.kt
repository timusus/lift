package com.acompany.weightr.features.exercises.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.acompany.data.model.Exercise

@Composable
fun EditExercisesAlert(exercise: Exercise, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(text = "Edit Exercise")
        },
        text = {
            Column {
                Spacer(Modifier.size(16.dp))
                TextField(
                    value = "",
                    onValueChange = { },
                    label = { Text("One rep max") }
                )
                Spacer(Modifier.size(16.dp))
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onDismiss()
                }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }) {
                Text("Close")
            }
        }
    )
}