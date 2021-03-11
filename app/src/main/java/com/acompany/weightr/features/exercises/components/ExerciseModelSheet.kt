package com.acompany.weightr.features.exercises.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

interface ModelSheetScope {
    fun openDrawer()
    fun closeDrawer()
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun rememberModelSheetScope(sheetState: ModalBottomSheetState): ModelSheetScope {
    val scope = rememberCoroutineScope()
    return remember(scope) {
        object : ModelSheetScope {

            override fun openDrawer() {
                scope.launch {
                    sheetState.show()
                }
            }

            override fun closeDrawer() {
                scope.launch {
                    sheetState.hide()
                }
            }

        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun ExerciseModelSheet(
    initialValue: ModalBottomSheetValue = ModalBottomSheetValue.Hidden,
    sheetState: ModalBottomSheetState = rememberModalBottomSheetState(initialValue),
    sheetContent: @Composable ModelSheetScope.() -> Unit,
    content: @Composable ModelSheetScope.() -> Unit
) {
    val modelSheetScope = rememberModelSheetScope(sheetState)
    ModalBottomSheetLayout(
        sheetState = sheetState,
        scrimColor = Color.Transparent,
        sheetContent = {
            Column(
                modifier = Modifier
                    .heightIn(min = 120.dp)
                    .padding(24.dp)
            ) {
                modelSheetScope.sheetContent()
                Spacer(Modifier.size(16.dp))
            }
        },
        content = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                modelSheetScope.content()
            }
        }
    )
}