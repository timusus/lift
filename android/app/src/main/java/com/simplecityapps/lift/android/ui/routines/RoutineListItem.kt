package com.simplecityapps.lift.android.ui.routines

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simplecityapps.lift.android.ui.components.completionStatus
import com.simplecityapps.lift.android.ui.fake.FakeData
import com.simplecityapps.lift.android.ui.theme.AppTheme
import com.simplecityapps.lift.common.model.Routine
import com.simplecityapps.lift.common.model.Session

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineListItem(
    routine: Routine,
    session: Session?,
    isCurrent: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = if (isCurrent) CardDefaults.cardElevation(8.dp) else CardDefaults.cardElevation(),
        onClick = onClick
    ) {
        Row(
            modifier
                .heightIn(min = 72.dp)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.FitnessCenter,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentDescription = null,
                tint = if (isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer

            )
            Spacer(modifier = Modifier.size(16.dp))
            Column(modifier.weight(1f)) {
                Text(
                    text = routine.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.size(2.dp))
                Text(
                    text = session.completionStatus(),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.size(6.dp))
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = routine.exercises.joinToString("\n") { "- " + it.exercise.name },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Preview
@Composable
private fun RoutineListItemPreview() {
    AppTheme {
        RoutineListItem(
            FakeData.routines.first(),
            FakeData.sessions.first(),
            true
        )
    }
}