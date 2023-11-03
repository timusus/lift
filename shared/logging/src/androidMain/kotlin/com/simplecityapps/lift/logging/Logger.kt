package com.simplecityapps.lift.logging

import logcat.asLog
import logcat.logcat

actual typealias LogPriority = logcat.LogPriority

actual inline fun Any.logcat(
    priority: LogPriority,
    tag: String?,
    message: () -> String
) {
    logcat(priority, tag, message)
}

actual inline fun logcat(
    tag: String,
    priority: LogPriority,
    message: () -> String
) {
    logcat(tag, priority, message)
}

actual fun Throwable.asLog(): String {
    return asLog()
}