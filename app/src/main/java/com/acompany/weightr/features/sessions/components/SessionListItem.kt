package com.acompany.weightr.features.sessions.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.acompany.data.model.Session
import com.acompany.weightr.common.components.CircleIcon
import com.acompany.weightr.features.sessions.data.SessionPreviewProvider
import com.acompany.weightr.theme.MaterialColors
import com.acompany.weightr.theme.MaterialTypography

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun SessionListItem(
    session: Session,
    modifier: Modifier = Modifier,
    onSessionClick: () -> Unit = {}
) {
    CompositionLocalProvider(LocalContentColor provides MaterialColors.primary) {
        ListItem(
            icon = {
                CircleIcon(
                    icon = Icons.Rounded.FitnessCenter,
                    modifier = Modifier.padding(8.dp)
                )
            },
            text = {
                Text(
                    text = session.name,
                    style = MaterialTypography.body1
                )
            },
            secondaryText = {
                Text(
                    text = session.exercises.joinToString(", ") { it.name },
                    style = MaterialTypography.body2
                )
            },
            modifier = modifier
                .background(color = MaterialColors.surface)
                .clickable(onClick = onSessionClick)
        )
    }
}

@Preview
@Composable
private fun SessionListItemPreview(
    @PreviewParameter(SessionPreviewProvider::class) preview: Pair<Colors, Session>
) {
    MaterialTheme(colors = preview.first) {
        SessionListItem(session = preview.second)
    }
}