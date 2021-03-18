package com.acompany.lift.features.sessions.data

import androidx.lifecycle.ViewModel
import com.acompany.lift.common.DateFormatter
import com.acompany.lift.data.AppRepository
import com.acompany.lift.data.model.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SessionScreenViewModel @Inject constructor(
    private val appRepository: AppRepository,
    val dateFormatter: DateFormatter
) : ViewModel() {

    fun getSessions(): Flow<List<Session>> = appRepository.getAllSessions()
}