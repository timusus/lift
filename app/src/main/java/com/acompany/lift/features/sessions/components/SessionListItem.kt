package com.acompany.lift.features.sessions.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.acompany.lift.common.DateFormatter
import com.acompany.lift.common.components.CutCornerIcon
import com.acompany.lift.data.model.Session
import com.acompany.lift.di.AppModule
import com.acompany.lift.features.sessions.data.SessionItemPreviewProvider
import com.acompany.lift.theme.MaterialColors
import com.acompany.lift.theme.MaterialTypography

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun SessionListItem(
    session: Session,
    modifier: Modifier = Modifier,
    dateFormatter: DateFormatter,
    onSessionClick: () -> Unit = {}
) {
    CompositionLocalProvider(LocalContentColor provides MaterialColors.primary) {
        Card(
            shape = CutCornerShape(topStart = 8.dp, topEnd = 4.dp, bottomStart = 4.dp, bottomEnd = 4.dp),
            backgroundColor = MaterialTheme.colors.surface,
            modifier = modifier.clickable(onClick = onSessionClick)
        ) {
            Row(
                modifier
                    .heightIn(min = 72.dp)
                    .clickable(onClick = onSessionClick)
                    .padding(16.dp)
            ) {
                CutCornerIcon(
                    icon = Icons.Rounded.FitnessCenter,
                    modifier = Modifier.padding(8.dp),
                    tint = MaterialColors.primary
                )
                Spacer(modifier = Modifier.size(16.dp))
                Column(modifier.weight(1f)) {
                    Text(
                        text = session.routine.name,
                        style = MaterialTypography.body1,
                        color = MaterialColors.onBackground
                    )
                    Text(
                        text = dateFormatter.formatShortDateTime(session.startDate),
                        style = MaterialTypography.body2,
                        color = MaterialColors.onBackground.copy(alpha = 0.85f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SessionListItemPreview(
    @PreviewParameter(SessionItemPreviewProvider::class) preview: Pair<Colors, Session>
) {
    MaterialTheme(colors = preview.first) {
        SessionListItem(
            session = preview.second,
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