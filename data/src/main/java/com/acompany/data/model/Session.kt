package com.acompany.data.model

data class Session(
    val id: Int,
    val name: String,
    val exercises: List<Exercise>
)