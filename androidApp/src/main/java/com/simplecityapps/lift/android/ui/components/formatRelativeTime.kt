package com.simplecityapps.lift.android.ui.components

import android.icu.text.RelativeDateTimeFormatter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@Composable
fun formatRelativeTime(endDate: Instant): String {
    val now = Clock.System.now()
    val elapsedDuration = now - endDate
    val elapsedMinutes = elapsedDuration.inWholeMinutes
    val elapsedHours = elapsedDuration.inWholeHours
    val elapsedDays = elapsedDuration.inWholeDays
    val elapsedWeeks = elapsedDays / 7
    val elapsedMonths = elapsedDays / 30
    val elapsedYears = elapsedDays / 365

    val formatter = remember {
        RelativeDateTimeFormatter.getInstance()
    }

    val direction = if (now >= endDate) {
        RelativeDateTimeFormatter.Direction.LAST
    } else {
        RelativeDateTimeFormatter.Direction.NEXT
    }

    return when {
        elapsedYears > 1 -> formatter.format(
            elapsedYears.toDouble(),
            direction,
            RelativeDateTimeFormatter.RelativeUnit.YEARS
        )

        elapsedMonths > 1 -> formatter.format(
            elapsedMonths.toDouble(),
            direction,
            RelativeDateTimeFormatter.RelativeUnit.MONTHS
        )

        elapsedWeeks > 1 -> formatter.format(
            elapsedWeeks.toDouble(),
            direction,
            RelativeDateTimeFormatter.RelativeUnit.WEEKS
        )

        elapsedDays > 1 -> formatter.format(
            elapsedDays.toDouble(),
            direction,
            RelativeDateTimeFormatter.RelativeUnit.DAYS
        )

        elapsedHours >= 1 -> formatter.format(
            elapsedHours.toDouble(),
            direction,
            RelativeDateTimeFormatter.RelativeUnit.HOURS
        )

        elapsedMinutes in 1..59 -> formatter.format(
            elapsedMinutes.toDouble(),
            direction,
            RelativeDateTimeFormatter.RelativeUnit.MINUTES
        )

        else -> "Moments ago"
    }
}