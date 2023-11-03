package com.simplecityapps.lift.common.model

data class User(
    val id: String,
    val displayName: String?,
    val email: String?,
    val photoUrl: String?
)