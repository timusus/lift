package com.simplecityapps.lift.model

import kotlinx.datetime.Instant

actual interface InstantMapper {
    actual fun toData(value: Instant?): Any?
    actual fun <T : Any> toInstant(value: T?): Instant?
}