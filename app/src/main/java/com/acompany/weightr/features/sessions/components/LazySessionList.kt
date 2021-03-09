package com.acompany.weightr.features.sessions.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Colors
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.acompany.data.model.Session
import com.acompany.weightr.features.sessions.data.SessionListPreviewProvider

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

@Preview
@Composable
private fun SessionListItemPreview(
    @PreviewParameter(SessionListPreviewProvider::class) preview: Pair<Colors, List<Session>>
) {
    MaterialTheme(colors = preview.first) {
        LazySessionList(sessions = preview.second)
    }
}