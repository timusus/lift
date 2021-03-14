package com.acompany.lift.features.routines.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acompany.lift.data.AppRepository
import com.acompany.lift.data.model.Routine
import com.acompany.lift.data.model.RoutineExercise
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineScreenViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    val allRoutines: MutableStateFlow<List<Routine>> = MutableStateFlow(emptyList())

    init {
        viewModelScope.launch {
            appRepository.getAllRoutines().collect { routines ->
                allRoutines.emit(routines)
            }
        }
    }
}

@Composable
fun RoutineScreen(
    viewModel: RoutineScreenViewModel,
    onRoutineSelected: (Routine) -> Unit
) {

    val routines = viewModel.allRoutines.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Routines") },
            )
        },
        content = {
            RoutineList(
                routines = routines.value,
                modifier = Modifier.padding(8.dp),
                onRoutineClick = { routine ->
                    onRoutineSelected(routine)
                }
            )
        }
    )
}