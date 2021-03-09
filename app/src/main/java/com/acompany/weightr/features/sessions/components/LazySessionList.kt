package com.acompany.weightr.features.sessions.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.acompany.data.model.Session
import com.acompany.weightr.features.sessions.components.SessionListItem

@Composable
fun LazySessionList(
    sessions: List<Session>,
    modifier: Modifier = Modifier,
    onSessionClick: (Session) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        items(sessions) { session ->
            SessionListItem(session = session) {
                onSessionClick(session)
            }
            Divider()
        }
    }
}