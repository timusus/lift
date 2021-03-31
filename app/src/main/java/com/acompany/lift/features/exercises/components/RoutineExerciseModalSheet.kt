package com.acompany.lift.features.exercises.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
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
                    // Todo: Remove. Workaround for https://issuetracker.google.com/issues/18159364
                    delay(250)
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
        sheetShape = CutCornerShape(topStart = 8.dp, topEnd = 4.dp, bottomStart = 4.dp, bottomEnd = 4.dp),
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
            Box {
                modalSheetScope.content()
            }
        }
    )
    BackHandler(sheetState.isVisible) {
        modalSheetScope.hide()
    }
}