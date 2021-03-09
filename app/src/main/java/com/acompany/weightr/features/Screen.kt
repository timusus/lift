package com.acompany.weightr.features

sealed class Screen(val route: String, val name: String) {
    object Sessions : Screen("sessions", "Sessions")
    object Exercises : Screen("sessions/{sessionId}/exercises", "Exercises")
}