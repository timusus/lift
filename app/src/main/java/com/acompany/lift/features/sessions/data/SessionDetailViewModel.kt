package com.acompany.lift.features.sessions.data

import androidx.lifecycle.ViewModel
import com.acompany.lift.data.AppRepository
import com.acompany.lift.data.model.Session
import com.acompany.lift.di.AppModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionDetailViewModel @Inject constructor(
    val appRepository: AppRepository,
    @AppModule.AppCoroutineScope val appCoroutineScope: CoroutineScope
) : ViewModel() {

    fun deleteSession(session: Session) {
        appCoroutineScope.launch { // Use AppCoroutineScope here, as ViewModel may go out of scope during delete
            appRepository.deleteSession(session)
        }
    }
}