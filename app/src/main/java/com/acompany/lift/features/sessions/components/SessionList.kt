package com.acompany.lift.features.sessions.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.acompany.lift.common.DateFormatter
import com.acompany.lift.data.model.Session
import com.acompany.lift.di.AppModule
import com.acompany.lift.features.sessions.data.SessionListPreviewProvider

@Composable
fun SessionList(
    sessions: List<Session>,
    modifier: Modifier = Modifier,
    dateFormatter: DateFormatter
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sessions) { session ->
            SessionListItem(
                session = session,
                modifier = modifier,
                dateFormatter = dateFormatter,
                onSessionClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun SessionListPreview(
    @PreviewParameter(SessionListPreviewProvider::class) preview: Pair<Colors, List<Session>>
) {
    MaterialTheme(colors = preview.first) {
        SessionList(
            sessions = preview.second,
            dateFormatter = DateFormatter(
                context = LocalContext.current,
                mediumDateFormatter = AppModule.provideMediumDateFormat(),
                shortDateFormatter = AppModule.provideShortDateFormat(),
                mediumDateTimeFormatter = AppModule.provideMediumDateTimeFormat(),
                shortDateTimeFormatter = AppModule.provideShortDateTimeFormat()
            )
        )
    }
}
