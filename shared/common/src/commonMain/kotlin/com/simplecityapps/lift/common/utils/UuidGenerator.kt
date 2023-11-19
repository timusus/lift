package com.simplecityapps.lift.common.utils

expect object UuidGenerator {

    fun generateUuid(): String
    fun generateSeededUuid(value: Long): String
}