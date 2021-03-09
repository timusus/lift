package com.acompany.weightr.features.sessions.data

import com.acompany.data.model.Session
import com.acompany.weightr.features.exercises.data.DummyExercises

object DummySessions {
    val sessions = listOf(
        Session(0, "Day 1", DummyExercises.exercises.filter { it.sessionId == 0 }),
        Session(1, "Day 2", DummyExercises.exercises.filter { it.sessionId == 1 }))
}