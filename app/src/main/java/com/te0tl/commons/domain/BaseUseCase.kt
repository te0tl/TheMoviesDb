package com.te0tl.commons.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Base abstraction class for a use case:
 * D: Data object, the success result of
 * E: Error object, the failure result of
 * Output Error object, exceptions should be managed in da house, not to throw it.
 *
 * The process is executed in the Default Dispatcher.
 */
abstract class BaseUseCase<out D, out E> {

    protected abstract suspend fun execute(): Result<D, E>

    suspend operator fun invoke(): Result<D, E> = withContext(Dispatchers.Default) {
        execute()
    }

}
