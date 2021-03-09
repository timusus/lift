package com.acompany.weightr.features.sessions.components

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.navigate
import com.acompany.data.model.Session
import com.acompany.weightr.features.Screen

class SessionScreen(sessions: () -> List<Session>) : Screen(
    route = "sessions",
    name = "Sessions",
    content = { paddingValues, navController, navBackStackEntry ->
        LazySessionList(
            sessions = sessions(),
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            onSessionClick = { session ->
                navController.navigate("sessions/${session.id}/exercises")
            }
        )
    }
)