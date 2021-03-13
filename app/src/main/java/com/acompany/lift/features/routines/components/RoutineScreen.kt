package com.acompany.lift.features.routines.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acompany.lift.data.AppRepository
import com.acompany.lift.data.model.Routine
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
    paddingValues: PaddingValues,
    onRoutineSelected: (Routine) -> Unit
) {

    val routines = viewModel.allRoutines.collectAsState()

    RoutineList(
        routines = routines.value,
        modifier = Modifier
            .padding(paddingValues)
            .padding(8.dp),
        onRoutineClick = { routine ->
            onRoutineSelected(routine)
        }
    )
}