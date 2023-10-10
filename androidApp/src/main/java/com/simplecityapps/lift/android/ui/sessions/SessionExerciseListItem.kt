package com.simplecityapps.lift.android.ui.sessions

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simplecityapps.lift.android.ui.theme.AppTheme
import com.simplecityapps.lift.android.ui.fake.FakeData
import com.simplecityapps.lift.model.SessionExercise

@Composable
fun SessionExerciseListItem(sessionExercise: SessionExercise) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = Icons.Outlined.FitnessCenter,
                    contentDescription = null
                )
                Spacer(
                    modifier = Modifier.size(16.dp)
                )
                Column {
                    Text(
                        text = sessionExercise.routineExercise.exercise.name
                    )
                    Spacer(
                        modifier = Modifier.size(2.dp)
                    )
                    Text(
                        text = "${sessionExercise.sets} sets x ${sessionExercise.reps} reps"
                    )
                }
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun SessionListItemPreview() {
    AppTheme(useDarkTheme = true) {
        Box(
            modifier = Modifier
                .heightIn(40.dp)
                .fillMaxWidth()
        ) {
            SessionExerciseListItem(FakeData.sessionExercises.first())
        }
    }
}