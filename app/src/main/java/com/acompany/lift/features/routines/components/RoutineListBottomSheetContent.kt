package com.acompany.lift.features.routines.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acompany.lift.common.components.StringTextField
import com.acompany.lift.data.model.Exercise
import com.acompany.lift.data.model.RoutineExercise
import com.acompany.lift.features.main.data.DummyAppRepository
import com.acompany.lift.theme.MaterialColors
import com.acompany.lift.theme.MaterialTypography
import com.acompany.lift.theme.ThemedPreviewProvider

@Composable
fun RoutineListBottomSheetContent(
    modifier: Modifier = Modifier,
    allExercises: List<Exercise>
) {
    val routineExercises: MutableList<RoutineExercise> = remember { mutableStateListOf() }

    var showAddExerciseDialog: Boolean by remember { mutableStateOf(false) }
    if (showAddExerciseDialog) {
        EditRoutineDialog(allExercises = allExercises) { routineExercise ->
            showAddExerciseDialog = false
            routineExercise?.let {
                routineExercises.add(routineExercise)
            }
        }
    }

    Box(modifier = modifier.padding(24.dp)) {
        Column(
            modifier = modifier
        ) {
            Text(
                modifier = modifier,
                text = "New Routine",
                style = MaterialTypography.subtitle1,
                color = MaterialColors.onSurface
            )
            Spacer(Modifier.size(16.dp))
            StringTextField(
                modifier = modifier.fillMaxWidth(),
                key = "routine_name",
                label = "Name") {
            }
            Spacer(Modifier.size(16.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Exercises",
                    style = MaterialTypography.subtitle1,
                    color = MaterialColors.onSurface
                )
                IconButton(modifier = modifier, onClick = { showAddExerciseDialog = true }) {
                    Icon(
                        imageVector = Icons.Rounded.AddCircle,
                        contentDescription = "Add exercise",
                        tint = MaterialColors.onSurface
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.heightIn(max = 400.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(routineExercises) { routineExercise ->
                    Card(
                        shape = CutCornerShape(topStart = 8.dp, topEnd = 4.dp, bottomStart = 4.dp, bottomEnd = 4.dp),
                        backgroundColor = MaterialTheme.colors.surface,
                        elevation = 1.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier.weight(1f)) {
                                Text(
                                    text = routineExercise.exercise.name,
                                    style = MaterialTypography.body1,
                                    color = MaterialColors.onBackground
                                )
                                Text(
                                    text = "${routineExercise.sets} sets x ${routineExercise.reps} reps",
                                    style = MaterialTypography.body2,
                                    fontSize = 16.sp,
                                    color = MaterialColors.onBackground.copy(alpha = 0.85f)
                                )
                            }
                            if (routineExercise.weight != null) {
                                Spacer(modifier.size(16.dp))
                                Text(
                                    text = routineExercise.weight?.let { weight -> "$weight kg" } ?: "Weight",
                                    style = MaterialTypography.body2,
                                    fontSize = 16.sp,
                                    color = MaterialColors.onBackground.copy(alpha = 0.85f)
                                )
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.size(32.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { /*TODO*/ }) {
                Text(text = "Save Routine")
            }
            Spacer(Modifier.size(16.dp))
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { /*TODO*/ }) {
                Text(text = "Cancel")
            }
        }
    }
}

@Preview
@Composable
private fun RoutineListBottomSheetContentPreview(
    @PreviewParameter(ThemedPreviewProvider::class) colors: Colors
) {
    MaterialTheme(colors = colors) {
        RoutineListBottomSheetContent(
            modifier = Modifier.background(colors.surface),
            allExercises = DummyAppRepository.exercises,
        )
    }
}