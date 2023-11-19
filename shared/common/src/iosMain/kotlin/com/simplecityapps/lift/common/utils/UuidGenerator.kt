package com.simplecityapps.lift.common.utils

import platform.Foundation.NSUUID

actual object UuidGenerator {
    actual fun generateUuid(): String {
        return NSUUID.UUID().UUIDString
    }

    actual fun generateSeededUuid(value: Long): String {
        TODO("Not yet implemented")
    }
}