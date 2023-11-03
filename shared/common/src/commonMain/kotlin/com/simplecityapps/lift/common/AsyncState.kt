package com.simplecityapps.lift.android.common

/**
 * A sealed class representing the various states of an asynchronous operation.
 * @param T the type of data associated with a successful operation
 */
sealed class AsyncState<out T> {
    /** Represents the idle state, when the operation has not yet started */
    object Idle : AsyncState<Nothing>()

    /** Represents the loading state, when the operation is in progress */
    object Loading : AsyncState<Nothing>()

    /** Represents a successful operation and contains the associated data */
    data class Success<out T>(val value: T) : AsyncState<T>()

    /** Represents a failed operation and contains the associated error */
    data class Failure(val throwable: Throwable) : AsyncState<Nothing>()
}

/**
 * Applies the given [transform] function to the [AsyncState.Success.value] of a [AsyncState.Success] instance and returns the result
 * wrapped in a new [AsyncState.Success] instance. For other instances of [AsyncState], this function returns the same instance.
 *
 * @return a new [AsyncState.Success] instance with the transformed value, or the input [AsyncState] instance
 */
inline fun <R, T> AsyncState<T>.map(transform: (value: T) -> R): AsyncState<R> {
    return when (this) {
        is AsyncState.Success -> {
            AsyncState.Success(transform(value))
        }
        is AsyncState.Failure -> this
        is AsyncState.Idle -> this
        is AsyncState.Loading -> this
    }
}