package com.simplecityapps.lift.common

import com.simplecityapps.lift.android.common.AsyncState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transformLatest

/**
 * A class that encapsulates an operation that may fail and provides the ability to retry the operation
 * in case of failures.
 *
 * @param T the type of data produced by the operation
 * @property flow the Flow that represents the operation
 */
class RetryableOperation<T>(
    private val flow: () -> Flow<T>
) {

    /**
     * Secondary constructor that creates a new RetryableOperation instance from a suspend function.
     *
     * @param block the suspend function that performs the operation
     */
    constructor(block: suspend () -> T) : this(flow = { block.asFlow() })

    private val retryEvent: MutableSharedFlow<Unit> = MutableSharedFlow(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val asyncFlow: Flow<AsyncState<T>> = retryEvent
        .onStart { emit(Unit) }
        .transformLatest {
            emit(AsyncState.Loading)
            emitAll(
                flow()
                    .map { result ->
                        AsyncState.Success(result)
                    }.catch { throwable ->
                        emit(AsyncState.Failure(throwable))
                    }
            )
        }

    /**
     * Requests a retry of the operation
     */
    fun retry() {
        retryEvent.tryEmit(Unit)
    }
}