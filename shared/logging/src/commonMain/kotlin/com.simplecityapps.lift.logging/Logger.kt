package com.simplecityapps.lift.logging

expect enum class LogPriority {
    VERBOSE,
    DEBUG,
    INFO,
    WARN,
    ERROR,
    ASSERT
}

// Called logcat, rather than a more generic name, because it's relatively unique
expect inline fun Any.logcat(
    priority: LogPriority = LogPriority.INFO,
    /**
     * If provided, the log will use this tag instead of the simple class name of `this` at the call
     * site.
     */
    tag: String? = null,
    message: () -> String
)

expect inline fun logcat(
    tag: String,
    priority: LogPriority = LogPriority.DEBUG,
    message: () -> String
)


expect fun Throwable.asLog(): String