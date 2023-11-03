package com.simplecityapps.lift.logging


actual enum class LogPriority {
    VERBOSE,
    DEBUG,
    INFO,
    WARN,
    ERROR,
    ASSERT
}

actual inline fun Any.logcat(
    priority: LogPriority,
    tag: String?,
    message: () -> String
) {
    tag?.let {
        println("$tag: ${message()}")
    } ?: run {
        println(message())
    }
}

actual inline fun logcat(
    tag: String,
    priority: LogPriority,
    message: () -> String
) {
    println("$tag: ${message()}")
}

actual fun Throwable.asLog(): String {
    return toString()
}