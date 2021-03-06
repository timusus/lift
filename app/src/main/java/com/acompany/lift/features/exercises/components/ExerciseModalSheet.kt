package com.acompany.lift.features.exercises.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface ModalSheetScope {
    fun show()
    fun hide()
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun rememberModalSheetScope(sheetState: ModalBottomSheetState): ModalSheetScope {
    val scope = rememberCoroutineScope()
    return remember(scope) {
        object : ModalSheetScope {
            override fun show() {
                scope.launch {
                    delay(250) // Todo: Remove. Workaround for https://issuetracker.google.com/issues/181593642
                    sheetState.show()
                }
            }

            override fun hide() {
                scope.launch {
                    sheetState.hide()
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun ExerciseModalSheet(
    initialValue: ModalBottomSheetValue = ModalBottomSheetValue.Hidden,
    sheetState: ModalBottomSheetState = rememberModalBottomSheetState(initialValue),
    sheetContent: @Composable ModalSheetScope.() -> Unit,
    content: @Composable ModalSheetScope.() -> Unit
) {
    val modalSheetScope = rememberModalSheetScope(sheetState)
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .heightIn(min = 120.dp)
                    .padding(24.dp)
            ) {
                modalSheetScope.sheetContent()
                Spacer(Modifier.size(16.dp))
            }
        },
        content = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                modalSheetScope.content()
            }
        }
    )
}