package com.simplecityapps.lift.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val liftDispatcher: LiftDispatchers)

enum class LiftDispatchers {
    Default,
    IO,
}
