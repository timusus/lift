package com.simplecityapps.lift.android.ui.components

import androidx.compose.runtime.Composable
import com.simplecityapps.lift.common.model.Session

@Composable
fun Session?.completionStatus(): String {
    return when {
        this == null -> {
            "Never"
        }

        isComplete -> {
            formatRelativeTime(endDate = (endDate ?: startDate))
        }

        else -> {
            "In progress"
        }
    }
}