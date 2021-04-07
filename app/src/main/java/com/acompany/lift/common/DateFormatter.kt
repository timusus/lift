package com.acompany.lift.common

import android.content.Context
import android.text.format.DateUtils
import com.acompany.lift.di.AppModule
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.DateFormat
import java.util.*
import javax.inject.Inject

class DateFormatter @Inject constructor(
    @ApplicationContext val context: Context,
    @AppModule.MediumDateFormat private val mediumDateFormatter: DateFormat,
    @AppModule.ShortDateFormat private val shortDateFormatter: DateFormat,
    @AppModule.MediumDateTimeFormat private val mediumDateTimeFormatter: DateFormat,
    @AppModule.ShortDateTimeFormat private val shortDateTimeFormatter: DateFormat
) {

    fun formatMediumDate(date: Date): String {
        return mediumDateFormatter.format(date)
    }

    fun formatMediumDateTime(date: Date): String {
        return mediumDateTimeFormatter.format(date)
    }

    fun formatShortDate(date: Date): String {
        return shortDateFormatter.format(date)
    }

    fun formatShortDateTime(date: Date): String {
        return shortDateTimeFormatter.format(date)
    }

    fun formatInterval(startDate: Date, endDate: Date): String {
        return DateUtils.formatDateRange(context, startDate.time, endDate.time, DateUtils.FORMAT_SHOW_TIME)
    }

}