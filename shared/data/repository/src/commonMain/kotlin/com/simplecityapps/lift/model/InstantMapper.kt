package com.simplecityapps.lift.model

import kotlinx.datetime.Instant

expect interface InstantMapper {
    fun toData(value: Instant?): Any?
    fun <T : Any> toInstant(value: T?): Instant?
}